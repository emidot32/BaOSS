import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ChartData, CohortAnalysis, ProductInstance} from '../_models/interface';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {

  constructor(private http: HttpClient) { }


  getUserNumber(startDate: string, endDate: string): Observable<number> {
    return this.http.get<number>(`${environment.analyticsServiceUrl}/statistic/user-number?start_date=${startDate}&end_date=${endDate}`);
  }
  getUserNumbersByProducts(startDate: string, endDate: string): Observable<ChartData> {
    return this.http.get<ChartData>(`${environment.analyticsServiceUrl}/statistic/user-number/products?start_date=${startDate}&end_date=${endDate}`);
  }
  getUsersByDate(startDate: string, endDate: string, step: string): Observable<ChartData> {
    return this.http.get<ChartData>(`${environment.analyticsServiceUrl}/statistic/users-by-date?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  }
  getProductsByDate(startDate: string, endDate: string, step: string): Observable<ChartData[]> {
    return this.http.get<ChartData[]>(`${environment.analyticsServiceUrl}/statistic/products-by-date?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  }

}
