import {Injectable} from '@angular/core';

import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';

import {User} from '../_models/interface';


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

  getAll() {
    return this.http.get<User[]>('/user-service/users');
  }

  register(user: User) {
    return this.http.post<User>(`${environment.apiUrl}/user-service/auth/sign-up`, user);
  }

  getUser(login: string) {
    return this.http.get<User>(`${environment.apiUrl}/profile/${login}`);
  }
}




