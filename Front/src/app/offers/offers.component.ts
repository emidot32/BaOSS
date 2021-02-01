import { Component, OnInit } from '@angular/core';
import {ConstantPrices, DtvOffer, InternetOffer, Tariff} from '../_models/interface';
import {OffersService} from '../_services/offers.service';
import {Router} from '@angular/router';
import {AuthService} from '../_services/auth.service';
import {ToastrService} from 'ngx-toastr';

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

  constructor(private offerService: OffersService,
              private router: Router,
              private authService: AuthService,
              private toaster: ToastrService) { }

  ngOnInit() {
    this.offerService.getInternetOffers()
        .subscribe(data => this.internetOffers = data,
            error => this.toaster.error(error.error.message));
    this.offerService.getDtvOffers()
        .subscribe(data => this.dtvOffers = data,
            error => this.toaster.error(error.error.message));
    this.offerService.getTariffs()
        .subscribe(data => this.tariffs = data,
            error => this.toaster.error(error.error.message));
    this.offerService.getConstantPrices()
        .subscribe(data => this.constantPrices = data,
            error => this.toaster.error(error.error.message));
  }

  goToOE() {
    if (this.authService.currentUserValue != null) {
      this.router.navigate(['/order-entry']);
    } else {
      this.router.navigate(['/login'], {queryParams: {nextPage: 'order-entry'}});
    }
  }

}
