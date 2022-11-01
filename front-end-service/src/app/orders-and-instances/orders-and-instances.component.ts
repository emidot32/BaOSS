import { Component, OnInit } from '@angular/core';
import {Order, ProductInstance} from '../_models/interface';
import {AuthService} from '../_services/auth.service';
import {OrderService} from '../_services/order.service';
import {InstanceService} from '../_services/instance.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-orders-and-instances',
  templateUrl: './orders-and-instances.component.html',
  styleUrls: ['./orders-and-instances.component.css']
})
export class OrdersAndInstancesComponent implements OnInit {
  allOrdersForUser: Order[];
  allInstancesForUser: ProductInstance[];
  
  constructor(private authService: AuthService,
              private orderService: OrderService,
              private instanceService: InstanceService,
              private router: Router) { }

  ngOnInit() {
    this.orderService.getAllOrdersForUser(this.authService.currentUserValue.id)
        .subscribe(data => this.allOrdersForUser = data);
    this.instanceService.getAllInstancesForUser(this.authService.currentUserValue.id)
        .subscribe(data => this.allInstancesForUser = data);
  }

  goToInstance(instance: ProductInstance) {
    this.router.navigate(['/homeath/instance/', instance.id], {queryParams: {product: instance.product}});
  }

}
