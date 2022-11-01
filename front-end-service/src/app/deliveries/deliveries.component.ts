import {Component, OnInit} from '@angular/core';
import {OrderService} from '../_services/order.service';
import {Delivery, Device, Order, OrderValue, UserDevice} from '../_models/interface';
import {AuthService} from '../_services/auth.service';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {DEFAULT_ERROR_MESSAGE} from '../_models/constants';

@Component({
    selector: 'app-deliveries',
    templateUrl: './deliveries.component.html',
    styleUrls: ['./deliveries.component.css']
})
export class DeliveriesComponent implements OnInit {
    todayDeliveries: Delivery[];
    macAddress: string;
    processingDelivery: Delivery;
    macAddressForm: FormGroup;
    validationMessages = {
        macAddress: [
            {type: 'required', message: 'MAC Address is required'},
            {type: 'minlength', message: 'MAC Address must be 17 characters long'},
            {type: 'maxlength', message: 'MAC Address must be 17 characters long'},
            {type: 'pattern', message: 'MAC Address should be like "00:00:00:00:00:00"'}
        ]
    };

    constructor(private orderService: OrderService,
                private toaster: ToastrService,
                private authService: AuthService) {
        this.macAddressForm = new FormGroup({
            macAddress: new FormControl('', [
                Validators.required,
                Validators.pattern('^[a-zA-Z0-9]{2}:[a-zA-Z0-9]{2}:[a-zA-Z0-9]{2}:[a-zA-Z0-9]{2}:[a-zA-Z0-9]{2}:[a-zA-Z0-9]{2}$'),
                Validators.minLength(17),
                Validators.maxLength(17)
            ])
        });
    }

    ngOnInit() {
        this.orderService.getTodayDeliveriesForEmployee(this.authService.currentUserValue.id)
            .subscribe(data => {
                this.todayDeliveries = data;
                console.log(data);
            });
        // for (const delivery of this.todayDeliveries) {
        //   if (delivery.deliveryStarted) {
        //     this.processingDelivery = delivery;
        //     break;
        //   }
        // }
        console.log(this.todayDeliveries);
    }

    getAddressStr(delivery: Delivery) {
        const address = delivery.address;
        let addressStr = address.country + ', ' + address.city + ', ' + address.street + ', ' + address.buildingNum
            + ', room: ' + address.roomNum;
        if (address.index != null) {
            addressStr += ', index: ' + address.index;
        }
        return addressStr;
    }

    sendEvent(delivery: Delivery) {
        const orderValue = {
            userId: this.authService.currentUserValue.id,
            selectedAddress: delivery.address,
            order: {id: delivery.orderId} as Order,
            deliveryId: delivery.id
        } as OrderValue;
        if (delivery.needInfo) {
            orderValue.macAddress = this.macAddressForm.controls.macAddress.value;
        } else {
            orderValue.macAddress = delivery.additionalInfo.macAddress;
        }
        console.log(orderValue.macAddress);
        this.orderService.sendDeliveryFinishEvent(orderValue)
            .subscribe(data => {
                for (let i = 0; i < this.todayDeliveries.length; i++) {
                    if (this.todayDeliveries[i].id == data.id) {
                        this.todayDeliveries[i] = data;
                    }
                }
                this.toaster.success('Delivery is completed!');
            }, error => {
                if (error.message != null && error.message != '') {
                    this.toaster.error(error.message);
                } else if (error.error.message != null && error.error.message != '') {
                    this.toaster.error(error.error.message);
                } else {
                    this.toaster.error(DEFAULT_ERROR_MESSAGE);
                }
            });
    }

    getValidationMessage(controlName: string) {
        const controlErrors: ValidationErrors = this.macAddressForm.get(controlName).errors;
        let error = null;
        if (controlErrors != null) {
            for (const controlError in controlErrors) {
                if (controlErrors[controlError]) {
                    error = this.validationMessages[controlName].find((valMsg) => {
                        return valMsg.type === controlError;
                    });
                    break;
                }
            }
        }
        return error;
    }
}
