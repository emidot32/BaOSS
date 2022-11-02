import {Component, OnInit} from '@angular/core';
import {Menu, User} from '../_models/interface';
import {AuthService} from '../_services/auth.service';
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


    constructor(private authenticationService: AuthService) {
        this.login = authenticationService.currentUserValue.login;
        this.role = authenticationService.currentUserValue.role;
    }

    public menu: Menu[] = [{name: 'Offers', url: 'offers'}];


    ngOnInit() {
        console.log(this.role);
        this.menu.push({name: 'Profile', url: `profile`});
        if (this.role == 'USER') {
            this.menu.push({name: 'Orders and Instances', url: `orders-and-instances`});
        }
        if (this.role == 'FITTER') {
            this.menu.push({name: 'Deliveries', url: `deliveries`});
        }
        if (this.role == 'SALES_MANAGER') {
        // if (this.role == 'USER') {
            this.menu.push({name: 'Analytics', url: `analytics`});
        }
    }
}
