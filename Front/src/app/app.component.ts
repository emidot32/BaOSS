import {Component} from '@angular/core';
import {Router} from '@angular/router';
import { google } from '@google/maps';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  // currentUser: User;

  constructor(
    // private router: Router,
    // private authenticationService: AuthService
  ) {
    // this.authenticationService.currentUser.subscribe(x => this.currentUser = x);

  }

  // logout() {
  //   this.authenticationService.logoutuser();
  //   this.router.navigate(['/login']);
  // }
}
