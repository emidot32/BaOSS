import {Component, OnInit} from '@angular/core';
import {Cable, ConstantPrices, Device, Discount, DtvOffer, InternetOffer, PhoneNumber, Tariff} from '../_models/interface';
import {OffersService} from '../_services/offers.service';
import {ToastrService} from 'ngx-toastr';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {ResourceService} from '../_services/resource.service';
import {error} from 'util';

@Component({
    selector: 'app-order-entry',
    templateUrl: './order-entry.component.html',
    styleUrls: ['./order-entry.component.css']
})
export class OrderEntryComponent implements OnInit {
    products = [
        {id: 0, name: 'Mobile Product', active: false, check: false},
        {id: 1, name: 'Internet Product', active: false, check: false},
        {id: 2, name: 'DTV Product', active: false, check: false}
    ];
    internetOffers: InternetOffer[];
    dtvOffers: DtvOffer[];
    tariffs: Tariff[];
    constantPrices: ConstantPrices;
    activatedDiscounts: Discount[];
    phoneNumbers = [
        {phoneNumber: '0955672345', price: 0},
        {phoneNumber: '0955556666', price: 10},
    ];
    selectedPhoneNumber: PhoneNumber;
    selectedTariff: Tariff;
    support5g =  false;
    deliveryAndActivationMobile = false;
    selectedSpeed: InternetOffer;
    devices: Device[];
    selectedDevice: Device;
    selectedChannelNumber: DtvOffer;
    fixedIpSupport = false;
    ipv6Support = false;
    installation = true;
    cable: Cable;
    cableLengthForm = new FormGroup({
        cableLength: new FormControl('', [
            Validators.required,
            Validators.min(0),
            Validators.max(30)
        ])});
    validationMessages = [
        {type: 'required', message: 'Cable length is required'},
        {type: 'min', message: 'Cable length must be greater than 0'},
        {type: 'max', message: 'We don`t have so much cable :)'},
    ];
    cablePriceTotal = 0;

    constructor(private offerService: OffersService,
                private toaster: ToastrService,
                private resourceService: ResourceService) {
    }

    ngOnInit() {
        this.offerService.getActiveDiscounts()
            .subscribe(data => this.activatedDiscounts = data);
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

    selectProduct(productId: number) {
        this.products[productId].check = !this.products[productId].check;
        if (productId == 2 && !this.products[1].check) {
            this.products[1].check = true;
        }
        if (productId == 1) {
            if (this.products[2].check) {
                this.products[2].check = false;
            }
            this.resourceService.getDevicesForSale()
                .subscribe(data => {
                    this.devices = data;
                    this.devices.splice(0, 0, {name: 'None', price: 0} as Device);
                });
            this.resourceService.getTwistedPairCable()
                .subscribe(data => {
                        this.cable = data;
                        this.cableLengthForm.controls.cableLength.setValue( 3);
                        this.cableLengthForm.controls.cableLength.setValidators([
                            Validators.required,
                            Validators.min(0),
                            Validators.max(data.length / 2)
                        ]);
                    },
                    error => {
                        this.toaster.error(error.error.message);
                        this.toaster.error('We can not provide you any cable');
                        this.cableLengthForm.controls.cableLength.setValue( 0);
                        this.cableLengthForm.controls.cableLength.disable();
                    });
        }
        if (productId == 0) {
            this.resourceService.getPhoneNumbersForSale()
                .subscribe(data => this.phoneNumbers = data,
                    error => {
                        this.toaster.error(error.error.message);
                        this.toaster.error('Mobile product is not available at the moment');
                        this.products[productId].check = false;
                    });
        }
    }

    calculateTotalCablePrice() {
        const length = +this.cableLengthForm.controls.cableLength.value;
        if (length > 3) {
            this.cablePriceTotal = +((length - 3) * this.constantPrices.cableOneMeterPrice[1]).toFixed(2);
        } else {
            this.cablePriceTotal = 0;
        }
    }

    submit() {

    }

    calculateClassForMobile() {
        const classes = {
            first: false,
            second: false,
            third: false,
            mobile_block: true
        };
        if (this.products[1].check) {
            if (this.products[2].check) {
                classes.third = true;
            } else {
                classes.second = true;
            }
        } else {
            classes.first = true;
        }
        return classes;
    }

    getValidationMessage() {
        const controlErrors: ValidationErrors = this.cableLengthForm.get('cableLength').errors;
        let error = null;
        if (controlErrors != null) {
            for (const controlError in controlErrors) {
                if (controlErrors[controlError]) {
                    error = this.validationMessages.find((valMsg) => {
                        return valMsg.type === controlError;
                    });
                    break;
                }
            }
        }
        return error;
    }
}
