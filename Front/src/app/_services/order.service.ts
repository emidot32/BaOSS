import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OrderValue, User, Task, Order} from '../_models/interface';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  getAvailableTimes(deliveryDateStr: string, products: string[]): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiUrl}/order-service/delivery/available-times?deliveryDate=${deliveryDateStr}&products=${products}`);
  }

  createOrder(orderValue: OrderValue): Observable<Order> {
    return this.http.post<Order>(`${environment.apiUrl}/order-service/order`, orderValue);
  }
}
