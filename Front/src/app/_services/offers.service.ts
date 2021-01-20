import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {InternetOffer, DtvOffer, Tariff, ConstantPrices} from '../_models/interface';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class OffersService {

  constructor(private http: HttpClient) {
  }

  getInternetOffers(): Observable<InternetOffer[]> {
    return this.http.get<InternetOffer[]>(`${environment.apiUrl}/offer-service/offers/internet-offers`);
  }

  getDtvOffers(): Observable<DtvOffer[]> {
    return this.http.get<DtvOffer[]>(`${environment.apiUrl}/offer-service/offers/dtv-offers`);
  }

  getTariffs(): Observable<Tariff[]> {
    return this.http.get<Tariff[]>(`${environment.apiUrl}/offer-service/offers/tariffs`);
  }

  getConstantPrices(): Observable<ConstantPrices> {
    return this.http.get<ConstantPrices>(`${environment.apiUrl}/offer-service/offers/constant-prices`);
  }
}
