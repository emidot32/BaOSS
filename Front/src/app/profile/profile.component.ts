import {Component, OnInit} from '@angular/core';
import {Address, BillingAccount, User} from '../_models/interface';
import {UserService} from '../_services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DatePipe, formatDate} from '@angular/common';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from '../_services/auth.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    user: User;
    billingAccounts: BillingAccount[];
    addressSelected: Address;
    baSelected: BillingAccount;
    regDate: string;
    birthday: string;
    addingAddress = false;
    addingBA = false;
    addressValidationForm: FormGroup;
    baValidationForm: FormGroup;

    validationMessages = {
        address: [
            {type: 'required', message: 'Address is required'},
            {type: 'pattern', message: 'Country, city, street, build number, room and index must be separate via ",". ' +
                    'Example: Ukraine, Kyiv, Shevchenka, 10, 100, 03045'}
        ],
        accountNumber: [
            {type: 'required', message: 'Account number is required'},
            {type: 'pattern', message: 'Account number must be 16 number length. Like 1010 1010 1010 1010'}
        ],
    };

    constructor(private userService: UserService,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private toaster: ToastrService,
                private authService: AuthService,
                private dateFormatPipe: DatePipe) {
        this.addressValidationForm = new FormGroup({
            address: new FormControl('', [
                Validators.required,
                Validators.pattern('^[A-Za-z ]{3,}, [A-Za-z ]{3,}, [A-Za-z ]{3,}, [0-9/A-Z]{1,6}, [0-9/A-Z]{1,6}[, 0-9]{0,10}$')]),
        });
        this.baValidationForm = new FormGroup({
            accountNumber: new FormControl('', [
                Validators.required,
                Validators.pattern('^[0-9]{16}$')]),
        });
    }

    ngOnInit() {
        const login = this.authService.currentUserValue.login;
        this.userService.getUser(login).subscribe( data => {
            this.user = data;
            this.regDate = this.dateFormatPipe.transform(data.regDate, 'dd/MM/yyyy');
            this.birthday = this.dateFormatPipe.transform(data.birthday, 'dd/MM/yyyy');
        },
            error => this.toaster.error(error.error.message));
        this.userService.getBillingAccountForUser(login).subscribe( data => this.billingAccounts = data);
    }

    edit() {
        this.router.navigate([`/homeath/profile/edit`]);
    }

    addAddress() {
        this.addingAddress = true;
    }

    confirmAddingAddress() {
        const strings: string[] = (this.addressValidationForm.controls.address.value + '').split(', ');
        const address = {id: null,
            country: strings[0],
            city: strings[1],
            street: strings[2],
            buildingNum: strings[3],
            roomNum: strings[4],
            index: +strings[5]
        };

        this.userService.addOrDeleteAddressForUser(this.user, address)
            .subscribe(data => {
                this.user = data;
                this.toaster.success('Address is successfully added');
            }, error => this.toaster.error(error.error.message));
        this.addressValidationForm.reset();
        this.addingAddress = false;
    }

    cancelAddingAddress() {
        this.addressValidationForm.reset();
        this.addingAddress = false;
    }

    deleteAddress() {
        this.userService.addOrDeleteAddressForUser(this.user, this.addressSelected)
            .subscribe(data => {
                this.user = data;
                this.toaster.success('Address is successfully deleted');
            }, error => this.toaster.error(error.error.message));
    }

    addBA() {
        this.addingBA = true;
    }

    confirmAddingBA() {
        const ba = {
            id: null,
            user: this.user,
            accountNumber: this.baValidationForm.controls.accountNumber.value + '',
            balance: null
        };
        this.userService.addOrDeleteBillingAccountForUser(ba)
            .subscribe(data => {
                this.billingAccounts = data;
                this.toaster.success('Billing account is successfully added');
            }, error => this.toaster.error(error.error.message));
        this.baValidationForm.reset();
        this.addingBA = false;
    }

    cancelAddingBA() {
        this.baValidationForm.reset();
        this.addingBA = false;
    }

    deleteBA() {
        this.userService.addOrDeleteBillingAccountForUser(this.baSelected)
            .subscribe(data => {
                this.billingAccounts = data;
                this.toaster.success('Billing account is successfully deleted');
            }, error => this.toaster.error(error.error.message));
    }

    getValidationMessage(controlName: string) {
        const addressControlForm = this.addressValidationForm.get(controlName);
        const baControlForm = this.baValidationForm.get(controlName);
        const controlErrors: ValidationErrors = addressControlForm != null ? addressControlForm.errors : baControlForm.errors;
        let error = null;
        if (controlErrors != null) {
            for (const controlError in controlErrors) {
                if (controlErrors[controlError]) {
                    error = this.validationMessages[controlName].find((valMsg) => {
                        return valMsg.type === controlError;
                    });
                    break;
                }
            }
        }
        return error;
    }
}
