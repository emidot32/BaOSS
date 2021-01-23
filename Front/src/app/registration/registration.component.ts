import {Component, OnInit} from '@angular/core';
import {UserService} from '../_services/user.service';
import {AlertService} from '../_services/alert.service';
import {Router} from '@angular/router';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Address, User} from '../_models/interface';
import {environment} from '../../environments/environment';
import {ToastrService} from 'ngx-toastr';

@Component({
    selector: 'app-registration',
    templateUrl: './registration.component.html',
    styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

    registerForm: FormGroup;
    model: User = {} as User;
    accountValidationMessages = {
        userLogin: [
            {type: 'required', message: 'Login is required'},
            {type: 'minlength', message: 'Login must be at least 2 characters long'},
            {type: 'maxlength', message: 'Login cannot be more than 30 characters long'},
            {type: 'pattern', message: 'Your login must contain only numbers and letters'}
        ],
        userName: [
            {type: 'required', message: 'Name is required'},
            {type: 'minlength', message: 'Name must be at least 2 characters long'},
            {type: 'maxlength', message: 'Name cannot be more than 50 characters long'},
            {type: 'pattern', message: 'Your name must contain only letters'}
        ],
        userSurname: [
            {type: 'required', message: 'Surname is required'},
            {type: 'minlength', message: 'Surname must be at least 2 characters long'},
            {type: 'maxlength', message: 'Surname cannot be more than 50 characters long'},
            {type: 'pattern', message: 'Your surname must contain only letters'}
        ],
        userEmail: [
            {type: 'required', message: 'Email is required'},
            {type: 'pattern', message: 'Enter a valid email'}
        ],
        userPassword: [
            {type: 'required', message: 'Password is required'},
            {type: 'minlength', message: 'Password must be at least 6 characters long'},
            {type: 'maxlength', message: 'Your password cannot be more than 30 characters long'}
        ],
        phoneNumber: [
            {type: 'pattern', message: 'Phone number should be like +38 XXX XXX-XX-XX'}
        ],
        idCardNumber: [
            {type: 'required', message: 'Password is required'},
            {type: 'pattern', message: 'Id Card Number must be 10 characters long'}
        ],
        address: [
            {type: 'required', message: 'Password is required'},
            {type: 'pattern', message: 'Country, city, street, build number, room and index must be separate via ",". ' +
                    'Example: Ukraine, Kyiv, Shevchenka, 10, 100, 03045'}
        ],
    };

    constructor(private userService: UserService,
                private router: Router,
                private toastr: ToastrService) {

        this.registerForm = new FormGroup({

            userLogin: new FormControl('', [
                Validators.required,
                Validators.pattern('^[a-zA-Z0-9_]+$'),
                Validators.minLength(2),
                Validators.maxLength(30)
            ]),
            userEmail: new FormControl('', [
                Validators.required,
                Validators.pattern('^[_A-Za-z0-9-.]+@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$'),
                Validators.minLength(6),
                Validators.maxLength(30)
            ]),
            userName: new FormControl('', [
                Validators.required,
                Validators.pattern('^[a-zA-Z ]+$'),
                Validators.minLength(2),
                Validators.maxLength(50)]),
            userSurname: new FormControl('', [
                Validators.required,
                Validators.pattern('^[a-zA-Z ]+$'),
                Validators.minLength(2),
                Validators.maxLength(50)]),
            userPassword: new FormControl('', [
                Validators.required,
                Validators.minLength(3),
                Validators.maxLength(30)]),
            phoneNumber: new FormControl('', [
                Validators.pattern('^^[+]{1}[0-9]{2} [0-9]{3} [0-9]{3} [0-9]{2} [0-9]{2}$')]),
            idCardNumber: new FormControl('', [
                Validators.required,
                Validators.pattern('^[0-9]{10}$')]),
            address: new FormControl('', [
                Validators.required,
                Validators.pattern('^[A-Za-z ]{3,}, [A-Za-z ]{3,}, [A-Za-z ]{3,}, [0-9/A-Z]{1,6}, [0-9/A-Z]{1,6}[, 0-9]{0,10}$')]),
            gender: new FormControl(),
            birthday: new FormControl()
        });
    }

    ngOnInit() {
    }

    getValidationMessage(controlName: string) {
        const controlErrors: ValidationErrors = this.registerForm.get(controlName).errors;
        let error = null;
        if (controlErrors != null) {
            for (const controlError in controlErrors) {
                if (controlErrors[controlError]) {
                    error = this.accountValidationMessages[controlName].find((valMsg) => {
                        return valMsg.type === controlError;
                    });
                    break;
                }
            }
        }
        return error;
    }

    getAddressFromString(stringAddress: string): Address {
        const strings: string[] = (stringAddress + '').split(', ');
        return {id: null,
                country: strings[0],
                city: strings[1],
                street: strings[2],
                buildingNum: strings[3],
                roomNum: strings[4],
                index: +strings[5]
        };
    }


    register() {
        this.model.login = this.registerForm.controls.userLogin.value;
        this.model.name = this.registerForm.controls.userName.value;
        this.model.surname = this.registerForm.controls.userSurname.value;
        this.model.email = this.registerForm.controls.userEmail.value;
        this.model.password = this.registerForm.controls.userPassword.value;
        this.model.phoneNumber = this.registerForm.controls.phoneNumber.value;
        this.model.gender = this.registerForm.controls.gender.value;
        this.model.idCardNumber = this.registerForm.controls.idCardNumber.value;
        this.model.birthday = this.registerForm.controls.birthday.value;
        this.model.addresses = [this.getAddressFromString(this.registerForm.controls.address.value)];
        this.userService.register(this.model)
            .subscribe(
                () => {
                    this.toastr.success(`Registration is successful!`);
                    this.router.navigate(['/login']);
                },
                error => {
                    this.toastr.error(error.error.message);
                });
    }

}





