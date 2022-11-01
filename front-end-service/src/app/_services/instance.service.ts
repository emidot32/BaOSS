import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DtvProductInstance, InternetProductInstance, MobileProductInstance, Order, ProductInstance} from '../_models/interface';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InstanceService {

  constructor(private http: HttpClient) { }

  getAllInstancesForUser(userId: number): Observable<ProductInstance[]> {
    return this.http.get<ProductInstance[]>(`${environment.apiUrl}/order-service/instances/user/${userId}`);
  }

  getMobileProductInstance(mobileProductInstanceId: number): Observable<MobileProductInstance> {
    return this.http.get<MobileProductInstance>(`${environment.apiUrl}/order-service/instances/mobile-product/${mobileProductInstanceId}`);
  }

  getInternetProductInstance(internetProductInstanceId: number): Observable<InternetProductInstance> {
    return this.http.get<InternetProductInstance>(`${environment.apiUrl}/order-service/instances/internet-product/${internetProductInstanceId}`);
  }

  getDtvProductInstance(dtvProductInstanceId: number): Observable<DtvProductInstance> {
    return this.http.get<DtvProductInstance>(`${environment.apiUrl}/order-service/instances/dtv-product/${dtvProductInstanceId}`);
  }
}
