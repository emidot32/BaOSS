import { Component, OnInit } from '@angular/core';
import {ConstantPrices, DtvOffer, InternetOffer, Tariff} from '../_models/interface';
import {OffersService} from '../_services/offers.service';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {
  internetOffers: InternetOffer[];
  dtvOffers: DtvOffer[];
  tariffs: Tariff[];
  constantPrices: ConstantPrices;

  constructor(private offerService: OffersService) { }

  ngOnInit() {
    this.offerService.getInternetOffers()
        .subscribe(data => this.internetOffers = data);
    this.offerService.getDtvOffers()
        .subscribe(data => this.dtvOffers = data);
    this.offerService.getTariffs()
        .subscribe(data => this.tariffs = data);
    this.offerService.getConstantPrices()
        .subscribe(data => this.constantPrices = data);
    console.log(this.constantPrices);
  }

}
