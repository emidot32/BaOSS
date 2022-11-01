import { Component, OnInit } from '@angular/core';
import {OffersService} from '../_services/offers.service';
import {Router} from '@angular/router';
import {AuthService} from '../_services/auth.service';
import {ToastrService} from 'ngx-toastr';
import {AnalyticsService} from '../_services/analytics.service';
import {PRODUCTS} from '../_models/constants';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {
  // parameters
  startDate: Date;
  endDate: Date;
  step: string;
  analyticVariants = [
    {id: 0, name: 'Cohort analysis', active: false, check: false},
    {id: 1, name: 'Business metrics', active: false, check: false},
    {id: 2, name: 'Profit forecasting', active: false, check: false}
  ];
  // analytic results
  userNumbers: number;


  constructor(private analyticsService: AnalyticsService,
              private router: Router,
              private authService: AuthService,
              private toaster: ToastrService) { }

  ngOnInit() {
  }

}
