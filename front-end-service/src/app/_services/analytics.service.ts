import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ProductInstance} from '../_models/interface';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {

  constructor(private http: HttpClient) { }

  getUserNumber(): Observable<number> {
    return this.http.get<number>(`${environment.analyticsServiceUrl}/statistic/user-number`);
  }
}
