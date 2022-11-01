import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OrderValue, User, Task, Order, OrderWithInstances, Delivery} from '../_models/interface';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  getAvailableTimes(deliveryDateStr: string, products: string[]): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiUrl}/order-service/deliveries/available-times?deliveryDate=${deliveryDateStr}&products=${products}`);
  }

  createOrder(orderValue: OrderValue): Observable<Order> {
    return this.http.post<Order>(`${environment.apiUrl}/order-service/orders/create`, orderValue);
  }

  getAllOrdersForUser(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${environment.apiUrl}/order-service/orders?userId=${userId}`);
  }

  getOrderById(orderId: number): Observable<OrderWithInstances> {
    return this.http.get<OrderWithInstances>(`${environment.apiUrl}/order-service/orders/${orderId}`);
  }

  getTodayDeliveriesForEmployee(userId: number): Observable<Delivery[]> {
    return this.http.get<Delivery[]>(`${environment.apiUrl}/order-service/deliveries/${userId}`);
  }

  sendDeliveryFinishEvent(orderValue: OrderValue): Observable<Delivery> {
    return this.http.put<Delivery>(`${environment.apiUrl}/order-service/deliveries/finish`, orderValue);
  }
}
