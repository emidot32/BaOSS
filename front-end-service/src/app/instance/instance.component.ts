import { Component, OnInit } from '@angular/core';
import {DtvProductInstance, InternetProductInstance, MobileProductInstance} from '../_models/interface';
import {ActivatedRoute, Router} from '@angular/router';
import {OrderService} from '../_services/order.service';
import {InstanceService} from '../_services/instance.service';
import {PRODUCTS} from '../_models/constants';

@Component({
  selector: 'app-instance',
  templateUrl: './instance.component.html',
  styleUrls: ['./instance.component.css']
})
export class InstanceComponent implements OnInit {
  mobileProductInstance: MobileProductInstance;
  internetProductInstance: InternetProductInstance;
  dtvProductInstance: DtvProductInstance;
  id: number;
  product: string;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private instanceService: InstanceService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.id = params.instanceId;
    });
    this.activatedRoute.queryParams.subscribe(
        (queryParam: any) => {
          this.product = queryParam.product;
        }
    );

    if (this.product == PRODUCTS.MOBILE_PRODUCT) {
      this.instanceService.getMobileProductInstance(this.id)
          .subscribe( data => this.mobileProductInstance = data);
    } else if (this.product == PRODUCTS.INTERNET_PRODUCT) {
      this.instanceService.getInternetProductInstance(this.id)
          .subscribe( data => {this.internetProductInstance = data;});
    } else if (this.product == PRODUCTS.DTV_PRODUCT) {
      this.instanceService.getDtvProductInstance(this.id)
          .subscribe( data => this.dtvProductInstance = data);
    }
    console.log(this.internetProductInstance);
  }

  getAddressStr() {
    const address = this.internetProductInstance.address;
    console.log(address);
    let addressStr = address.country + ', ' + address.city + ', ' + address.street + ', ' + address.buildingNum + ', room: '
        + address.roomNum;
    if (address.index != null) {
      addressStr += ', index: ' + address.index;
    }
    return addressStr;
  }

}
