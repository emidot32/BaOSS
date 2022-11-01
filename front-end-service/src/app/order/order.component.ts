import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {OrderWithInstances} from '../_models/interface';
import {OrderService} from '../_services/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  order: OrderWithInstances;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private orderService: OrderService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.orderService.getOrderById(+params.orderId).subscribe(data => this.order = data);
    });
  }



}
