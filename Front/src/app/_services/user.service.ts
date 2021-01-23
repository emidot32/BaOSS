import {Injectable} from '@angular/core';

import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';

import {Address, BillingAccount, User} from '../_models/interface';


import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) {
    }

    escaping(str: string) {
        if (str == null) {
            return str;
        }
        return String(str).replace(/[&#$%~^*<>"'`=\/\\]/g, '');
    }

    getAllUsers() {
        return this.http.get<User[]>('/user-service/users');
    }

    register(user: User) {
        return this.http.post<User>(`${environment.apiUrl}/user-service/auth/sign-up`, user);
    }

    getUser(login: string): Observable<User> {
        return this.http.get<User>(`${environment.apiUrl}/user-service/user-info/${login}`);
    }

    getBillingAccountForUser(login: string): Observable<BillingAccount[]> {
        return this.http.get<BillingAccount[]>(`${environment.apiUrl}/billing-service/billing-accounts?login=${login}`);
    }

    addOrDeleteAddressForUser(user: User, address: Address): Observable<User> {
        return this.http.post<User>(`${environment.apiUrl}/user-service/user-info/user-address`, {user: user, address: address});
    }

    addOrDeleteBillingAccountForUser(billingAccount: BillingAccount): Observable<BillingAccount[]> {
        return this.http.post<BillingAccount[]>(`${environment.apiUrl}/billing-service/user-billing-account`, billingAccount);
    }

    edit(user: User) {
        return this.http.put<any>(`${environment.apiUrl}/user-service/user-info/update-user`, user);
    }
}




