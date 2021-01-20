import {Component, Input, OnInit} from '@angular/core';

import {Router} from '@angular/router';
import {AuthenticationService} from '../_services/authentication.service';
import {User} from '../_models/interface';
import {MenuComponent} from '../menu/menu.component';


@Component({
    selector: 'app-headerauth',
    templateUrl: './header-auth.component.html',
    styleUrls: ['./header-auth.component.css']
})
export class HeaderAuthComponent implements OnInit {
    currentUser: string;
    currentRole: string;


    constructor(private router: Router,
                private authenticationService: AuthenticationService) {
        // this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
        this.currentUser = this.authenticationService.currentUserValue.login;
        this.currentRole = this.authenticationService.currentUserValue.role;
    }

    ngOnInit() {
    }

    logout() {
        this.authenticationService.logoutUser();
        this.router.navigate(['home/offers']);
    }
}
