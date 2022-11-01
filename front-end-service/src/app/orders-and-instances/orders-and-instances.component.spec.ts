import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdersAndInstancesComponent } from './orders-and-instances.component';

describe('OrdersAndInstancesComponent', () => {
  let component: OrdersAndInstancesComponent;
  let fixture: ComponentFixture<OrdersAndInstancesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrdersAndInstancesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdersAndInstancesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
