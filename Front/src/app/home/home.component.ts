import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {User} from '../_models/interface';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    currentUser: User;

    constructor(private authService: AuthService) {
    }

    ngOnInit() {
        this.authService.currentUser.subscribe(data => this.currentUser = data);
    }

}
