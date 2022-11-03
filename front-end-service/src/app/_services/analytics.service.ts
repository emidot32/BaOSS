import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BusinessMetrics, ChartData, CohortAnalysis, ProductInstance} from '../_models/interface';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {

  constructor(private http: HttpClient) { }

  getCohortAnalysis(startDate: string, endDate: string, step: string): Observable<CohortAnalysis> {
    return this.http.get<CohortAnalysis>(`${environment.analyticsServiceUrl}/cohort-analysis?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  }
  getBusinessMetrics(startDate: string, endDate: string, step: string): Observable<BusinessMetrics> {
    return this.http.get<BusinessMetrics>(`${environment.analyticsServiceUrl}/business-metrics?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  }
  //
  // getUserNumber(startDate: string, endDate: string): Observable<number> {
  //   return this.http.get<number>(`${environment.analyticsServiceUrl}/statistic/user-number?start_date=${startDate}&end_date=${endDate}`);
  // }
  // getUserNumbersByProducts(startDate: string, endDate: string): Observable<ChartData> {
  //   return this.http.get<ChartData>(`${environment.analyticsServiceUrl}/statistic/user-number/products?start_date=${startDate}&end_date=${endDate}`);
  // }
  // getUsersByDate(startDate: string, endDate: string, step: string): Observable<ChartData> {
  //   return this.http.get<ChartData>(`${environment.analyticsServiceUrl}/statistic/users-by-date?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  // }
  // getProductsByDate(startDate: string, endDate: string, step: string): Observable<ChartData[]> {
  //   return this.http.get<ChartData[]>(`${environment.analyticsServiceUrl}/statistic/products-by-date?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  // }
  // getProfitsByDate(startDate: string, endDate: string, step: string): Observable<ChartData> {
  //   return this.http.get<ChartData>(`${environment.analyticsServiceUrl}/statistic/profit-by-date?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  // }
  // getProfit(startDate: string, endDate: string): Observable<number> {
  //   return this.http.get<number>(`${environment.analyticsServiceUrl}/statistic/profit?start_date=${startDate}&end_date=${endDate}`);
  // }
  // getAovsByDate(startDate: string, endDate: string, step: string): Observable<ChartData[]> {
  //   return this.http.get<ChartData[]>(`${environment.analyticsServiceUrl}/statistic/aovs-by-date?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  // }
  // getAovs(startDate: string, endDate: string): Observable<number[]> {
  //   return this.http.get<number[]>(`${environment.analyticsServiceUrl}/statistic/aovs?start_date=${startDate}&end_date=${endDate}`);
  // }
  // getArppuByDate(startDate: string, endDate: string, step: string): Observable<ChartData> {
  //   return this.http.get<ChartData>(`${environment.analyticsServiceUrl}/statistic/arppu-by-date?start_date=${startDate}&end_date=${endDate}&step=${step}`);
  // }
  // getArppu(startDate: string, endDate: string): Observable<number> {
  //   return this.http.get<number>(`${environment.analyticsServiceUrl}/statistic/arppu?start_date=${startDate}&end_date=${endDate}`);
  // }

}
