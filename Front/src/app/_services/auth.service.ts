import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';


import {environment} from '../../environments/environment';
import {User} from '../_models/interface';

const headers = new HttpHeaders({'Content-Type': 'application/json'});

@Injectable({providedIn: 'root'})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  public role: string;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public get getCurrentUserObservable(): Observable<User> {
      return this.currentUserSubject.asObservable();
  }


  login(login: string, password: string) {

    const body = {login: login, password: password};
    return this.http.post<any>(`${environment.apiUrl}/auth/sign-in`, body)
      .pipe(map(user => {
        this.role = user.role;
        console.log(`Signed in user: ${user}`);
        // store user details and jwt token in local storage to keep user logged in between reviewPage refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  logoutUser() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
