import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from '../_services/auth.service';
import {Router, ActivatedRoute} from '@angular/router';
import {UserService} from '../_services/user.service';
import {User} from '../_models/interface';
import {DEFAULT_ERROR_MESSAGE} from '../_models/constants';

@Component({
    selector: 'app-edit-profile',
    templateUrl: './edit-profile.component.html',
    styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

    user: User;
    login: string;

    form: FormGroup;
    profileValidationMessages = {
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
            {type: 'minlength', message: 'Password must be at least 3 characters long'},
            {type: 'maxlength', message: 'Your password cannot be more than 30 characters long'}
        ],
        phoneNumber: [
            {type: 'pattern', message: 'Phone number should be like +38 XXX XXX-XX-XX'}
        ],
    };

    constructor(private userService: UserService,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private toaster: ToastrService,
                private authService: AuthService) {
    }

    ngOnInit() {
        this.login = this.authService.currentUserValue.login;
        this.form = new FormGroup({
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
                Validators.minLength(3),
                Validators.maxLength(30)]),
            phoneNumber: new FormControl('', [
                Validators.pattern('^[+]{1}[0-9]{2} [0-9]{3} [0-9]{3} [0-9]{2} [0-9]{2}$')]),
        });

        this.userService.getUser(this.login)
            .subscribe(
                data => {
                    this.user = data;
                    this.form.controls.userName.setValue(this.user.name);
                    this.form.controls.userSurname.setValue(this.user.surname);
                    this.form.controls.userLogin.setValue(this.user.login);
                    this.form.controls.userEmail.setValue(this.user.email);
                    this.form.controls.phoneNumber.setValue(this.user.phoneNumber);
                },
                error => {
                    if (error.error.message != null && error.error.message != '') {
                        this.toaster.error(error.error.message);
                    } else if (error.message != null && error.message != '') {
                        this.toaster.error(error.message);
                    } else {
                        this.toaster.error(DEFAULT_ERROR_MESSAGE);
                    }
                });
    }

    edit() {
        this.user.name = this.form.controls.userName.value;
        this.user.surname = this.form.controls.userSurname.value;
        this.user.email = this.form.controls.userEmail.value;
        this.user.phoneNumber = this.form.controls.phoneNumber.value;
        this.user.login = this.form.controls.userLogin.value;
        if (this.form.controls.userPassword.touched) {
            this.user.password = this.form.controls.userPassword.value;
        }
        this.userService.edit(this.user)
            .subscribe(
                () => {
                    this.toaster.success(`Profile is successfully updated`);
                    this.router.navigate([`/homeath/profile`]);
                },
                error => {
                    if (error.message != null && error.message != '') {
                        this.toaster.error(error.message);
                    } else if (error.error.message != null && error.error.message != '') {
                        this.toaster.error(error.error.message);
                    } else {
                        this.toaster.error(DEFAULT_ERROR_MESSAGE);
                    }
                });
    }

    cancelEdit() {
        this.router.navigate([`/homeath/profile`]);
    }

    getValidationMessage(controlName: string) {
        const controlErrors: ValidationErrors = this.form.get(controlName).errors;
        let error = null;
        if (controlErrors != null) {
            for (const controlError in controlErrors) {
                if (controlErrors[controlError]) {
                    error = this.profileValidationMessages[controlName].find((valMsg) => {
                        return valMsg.type === controlError;
                    });
                    break;
                }
            }
        }
        return error;
    }

}
