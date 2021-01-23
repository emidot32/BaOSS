import {Component, OnInit} from '@angular/core';
import {Menu, User} from '../_models/interface';
import {AuthenticationService} from '../_services/authentication.service';
import {Observable} from 'rxjs';

// import {NotificationService} from "../_services/notification.service";


@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
    public role: string;
    public login: string;


    constructor(private authenticationService: AuthenticationService) {
        this.login = authenticationService.currentUserValue.login;
        this.role = authenticationService.currentUserValue.role;
    }

    public menu: Menu[] = [{name: 'Offers', url: 'offers'}];


    ngOnInit() {
        console.log(this.login);
        this.menu.push({name: 'Profile', url: `profile/${this.login}`});
    }
}
