import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';

import {AuthService} from '../_services/auth.service';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authenticationService: AuthService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser) {
      // logged in so return true
      return true;
    }

    // not logged in so redirect to login reviewPage with the return url
    this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
    return false;
  }
}
