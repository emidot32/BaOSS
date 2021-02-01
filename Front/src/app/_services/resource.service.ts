import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Cable, Device, DtvOffer, PhoneNumber} from '../_models/interface';
import {environment} from '../../environments/environment';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  constructor(private http: HttpClient) { }

  getDevicesForSale(): Observable<Device[]> {
    return this.http.get<Device[]>(`${environment.apiUrl}/resource-service/devices`);
  }

  getPhoneNumbersForSale(): Observable<PhoneNumber[]> {
    return this.http.get<PhoneNumber[]>(`${environment.apiUrl}/resource-service/phone-numbers`);
  }

  getTwistedPairCable(): Observable<Cable> {
    return this.http.get<Cable>(`${environment.apiUrl}/resource-service/cables/twisted-pair-cable`);
  }
}
