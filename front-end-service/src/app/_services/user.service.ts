import {Injectable} from '@angular/core';

import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';

import {Address, User} from '../_models/interface';


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
        return this.http.get<User>(`${environment.apiUrl}/user-service/users/${login}`);
    }

    getAddressesByLogin(login: string): Observable<Address[]> {
        return this.http.get<Address[]>(`${environment.apiUrl}/user-service/users/addresses?login=${login}`);
    }

    // getBillingAccountForUser(login: string): Observable<BillingAccount[]> {
    //     return this.http.get<BillingAccount[]>(`${environment.apiUrl}/billing-service/billing-accounts?login=${login}`);
    // }

    addAddressForUser(user: User, address: Address): Observable<User> {
        return this.http.post<User>(`${environment.apiUrl}/user-service/users/user-address`, {user: user, address: address});
    }

    removeAddressForUser(userId: number, addressId: number): Observable<User> {
        return this.http.delete<User>(`${environment.apiUrl}/user-service/users/${userId}/address/${addressId}`);
    }

    // addOrDeleteBillingAccountForUser(billingAccount: BillingAccount): Observable<BillingAccount[]> {
    //     return this.http.post<BillingAccount[]>(`${environment.apiUrl}/billing-service/user-billing-account`, billingAccount);
    // }

    edit(user: User) {
        return this.http.put<any>(`${environment.apiUrl}/user-service/users`, user);
    }
}




