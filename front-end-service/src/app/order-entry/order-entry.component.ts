import {Component, OnInit, Pipe, PipeTransform} from '@angular/core';
import {
    Address,
    Cable,
    ConstantPrices,
    Device,
    Discount,
    DtvOffer,
    InternetOffer, OrderValue,
    PhoneNumber,
    Tariff
} from '../_models/interface';
import {DEFAULT_ERROR_MESSAGE, PRODUCTS} from '../_models/constants';
import { DatePipe } from '@angular/common';
import {OffersService} from '../_services/offers.service';
import {ToastrService} from 'ngx-toastr';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {ResourceService} from '../_services/resource.service';
import {error} from 'util';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {DeviceInfoComponent} from '../device-info/device-info.component';
import {AuthService} from '../_services/auth.service';
import {UserService} from '../_services/user.service';
import {Location} from '@angular/common';
import {Router} from '@angular/router';
import {OrderService} from '../_services/order.service';

@Component({
    selector: 'app-order-entry',
    templateUrl: './order-entry.component.html',
    styleUrls: ['./order-entry.component.css']
})
export class OrderEntryComponent implements OnInit {
    products = [
        {id: 0, name: PRODUCTS.MOBILE_PRODUCT, active: false, check: false},
        {id: 1, name: PRODUCTS.INTERNET_PRODUCT, active: false, check: false},
        {id: 2, name: PRODUCTS.DTV_PRODUCT, active: false, check: false}
    ];
    internetOffers: InternetOffer[];
    dtvOffers: DtvOffer[];
    tariffs: Tariff[];
    constantPrices: ConstantPrices;
    activatedDiscounts: Discount[];
    phoneNumbers: PhoneNumber[];
    selectedPhoneNumber: PhoneNumber;
    selectedTariff: Tariff;
    support5g =  false;
    deliveryAndActivationMobile = true;
    selectedSpeed: InternetOffer;
    devices: Device[];
    selectedDevice: Device;
    selectedChannelNumber: DtvOffer;
    fixedIpSupport = false;
    ipv6Support = false;
    installation = false;
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
    userAddresses: Address[];
    selectedAddress: Address;
    // billingAccounts: BillingAccount[];
    // selectedAccount: BillingAccount;
    homeDelivery = true;
    storeAddress = '';
    deliveryDate: Date;
    deliveryTime: string;
    availableTimes: string[];
    dateErrorMessage: string;
    totalNRC: number;
    totalMRC: number;
    deliveryPrice = 0;

    constructor(private offerService: OffersService,
                private toaster: ToastrService,
                private resourceService: ResourceService,
                public dialog: MatDialog,
                private authService: AuthService,
                private userService: UserService,
                private location: Location,
                private router: Router,
                private orderService: OrderService,
                private dateFormatPipe: DatePipe) {
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
        this.userService.getAddressesByLogin(this.authService.currentUserValue.login)
            .subscribe(data => {
                    this.userAddresses = data;
                    this.selectedAddress = data[0];
                },
                error => {
                    if (error.message != null && error.message != '') {
                        this.toaster.error(error.message);
                    } else if (error.error.message != null && error.error.message != '') {
                        this.toaster.error(error.error.message);
                    } else {
                        this.toaster.error(DEFAULT_ERROR_MESSAGE);
                    }
                });
        // this.userService.getBillingAccountForUser(this.authService.currentUserValue.login)
        //     .subscribe(data => {
        //         if (data.length === 0) {
        //             this.noBillingAccountAction('You don\'t have any billing accounts.\n Please add some account.');
        //         } else {
        //             this.billingAccounts = data;
        //             this.selectedAccount = data[0];
        //         }},
        //         error => this.noBillingAccountAction(error.error.message));

    }

    submit() {
        const selectedProducts = this.getSelectedProductList();
        const selectedDevice = this.selectedDevice != null && this.selectedDevice.name == 'None' ? null : this.selectedDevice;
        const error = this.validateAll(selectedProducts);
        if (error != '') {
            this.toaster.error(error);
        } else {
            const orderValue = {userId: this.authService.currentUserValue.id,
                                selectedProducts: selectedProducts,
                                selectedPhoneNumber: this.selectedPhoneNumber,
                                selectedTariff: this.selectedTariff,
                                support5g: this.support5g,
                                deliveryAndActivationMobile: this.deliveryAndActivationMobile,
                                selectedSpeed: this.selectedSpeed,
                                selectedDevice: selectedDevice,
                                selectedChannelNumber: this.selectedChannelNumber,
                                fixedIpSupport: this.fixedIpSupport,
                                installation: this.selectedSpeed != null || this.selectedChannelNumber != null,
                                cableLength: +this.cableLengthForm.controls.cableLength.value,
                                cablePriceTotal: this.cablePriceTotal,
                                selectedAddress: this.selectedAddress,
                                deliveryDateStr: this.dateFormatPipe.transform(this.deliveryDate, 'dd/MM/yyyy'),
                                deliveryTime: this.deliveryTime,
                                totalNRC: this.totalNRC,
                                totalMRC: this.totalMRC,
                                deliveryPrice: this.deliveryPrice,
                                constantPrices: this.constantPrices,
                                orderAim: 'New'} as OrderValue;
            console.log(orderValue);
            this.orderService.createOrder(orderValue)
                .subscribe(data => {
                        this.toaster.success('Order is created');
                        this.router.navigate(['/homeath/orders-and-instances']);
                    },
                    error => {
                        if (error.message != null && error.message != '') {
                            this.toaster.error(error.message);
                        } else if (error.error.message != null && error.error.message != '') {
                            this.toaster.error(error.error.message);
                        } else {
                            this.toaster.error(DEFAULT_ERROR_MESSAGE);
                            this.router.navigate(['/homeath/orders-and-instances']);
                        }
                    });
        }
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
            const defaultDevice = {name: 'None', price: 0} as Device;
            if (this.devices == null) {
                this.resourceService.getDevicesForSale()
                    .subscribe(data => {
                        this.devices = data;
                        this.devices.splice(0, 0, defaultDevice);
                    });
            }
            this.selectedDevice = defaultDevice;
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
                        //this.toaster.error(error.error.message);
                        this.toaster.error('We can not provide you any cable');
                        this.cableLengthForm.controls.cableLength.setValue( 0);
                        this.cableLengthForm.controls.cableLength.disable();
                    });
        }
        if (productId == 0) {
            this.resourceService.getPhoneNumbersForSale()
                .subscribe(data => this.phoneNumbers = data,
                    error => {
                        //this.toaster.error(error.error.message);
                        this.toaster.error('Mobile product is not available at the moment');
                        this.products[productId].check = false;
                    });
        }
    }

    validateAll(selectedProducts: string[]): string {
        for (const item of selectedProducts) {
            if (item == PRODUCTS.MOBILE_PRODUCT) {
                if (this.selectedPhoneNumber == null) { return 'Please select phone number'; }
                if (this.selectedTariff == null) { return 'Please select tariff'; }
                if (this.deliveryAndActivationMobile && this.selectedAddress == null) { return 'Please select address for activation'; }
            }
            if (item == PRODUCTS.INTERNET_PRODUCT) {
                if (this.selectedSpeed == null) { return 'Please select speed of Internet'; }
                if (this.selectedAddress == null) { return 'Please select address for installation'; }
            }
            if (item == PRODUCTS.DTV_PRODUCT) {
                if (this.selectedChannelNumber == null) { return 'Please select number of channels'; }
            }
            // if (this.selectedAccount == null) { return 'Please select billing account'; }
            if (this.deliveryDate == null) {
                if ((this.deliveryAndActivationMobile || this.products[1].check) && this.deliveryTime == null) {
                    return 'Please select date and time';
                } else {
                    return 'Please select date';
                }
            }
        }
        return '';
    }


    validateAndGetAvailableTimes(value: Date) {
        const curDate = new Date();
        this.deliveryDate = value;
        if (curDate.getDay() === value.getDay()) {
            // this.dateErrorMessage = 'The date cannot be today';
            this.dateErrorMessage = '';
        } else if (value < curDate) {
            this.dateErrorMessage = 'The date cannot be earlier then today';
        } else if (value > this.addDays(curDate, 30)) {
            this.dateErrorMessage = 'The date cannot be more than 30 days from the current';
        } else {
            this.dateErrorMessage = '';
        }
        if (this.dateErrorMessage == '') {
            const selectedProducts = this.getSelectedProductList();
            this.orderService.getAvailableTimes(this.dateFormatPipe.transform(value, 'dd/MM/yyyy'), selectedProducts)
                .subscribe(data => this.availableTimes = data,
                    error => this.toaster.error(error.error.message));
        }
    }

    addDays(date: Date, days: number) {
        date.setDate(date.getDate() + days);
        return date;
    }

    calculateTotalPrices() {
        if (this.products[1].check) {
            this.deliveryPrice = this.constantPrices.installationPricesForInternetProduct[1];
        } else if (this.deliveryAndActivationMobile) {
            this.deliveryPrice = this.constantPrices.deliveryPrices[1];
        } else {
            this.deliveryPrice = 0;
        }
        this.calculateTotalCablePrice();
        this.calculateTotalMRC();
        this.calculateTotalNRC();
    }

    calculateTotalMRC() {
        this.totalMRC = 0;
        if (this.products[0].check && this.selectedTariff) {
            this.totalMRC += this.selectedTariff.discountedPrice;
        }
        if (this.products[1].check && this.selectedSpeed) {
            this.totalMRC += this.selectedSpeed.discountedPrice;
        }
        if (this.products[1].check && this.fixedIpSupport) {
            this.totalMRC += this.constantPrices.fixedIpPrices[1];
        }
        if (this.products[2].check && this.selectedChannelNumber) {
            this.totalMRC += this.selectedChannelNumber.discountedPrice;
        }
    }

    calculateTotalNRC() {
        this.totalNRC = this.deliveryPrice;
        if (this.products[0].check && this.selectedPhoneNumber) {
            this.totalNRC += this.selectedPhoneNumber.price;
        }
        if (this.products[0].check && this.support5g) {
            this.totalNRC += this.constantPrices.supportOf5gPrices[1];
        }
        if (this.products[1].check) {
            this.totalNRC += this.cablePriceTotal;
            if (this.selectedDevice) {
                if (this.selectedDevice.name !== 'None') {
                    this.totalNRC += this.selectedDevice.price;
                }
            }
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


    noBillingAccountAction(message: string) {
        this.toaster.error(message);
        this.router.navigate([`/homeath/profile`]);
    }

    openDeviceInfoWindow() {
        this.dialog.open(DeviceInfoComponent, {data: {device: this.selectedDevice}});
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

    getSelectedProductList(): string[] {
        const selectedProducts = [];
        for (const item of this.products) {
            if (item.check) {
                selectedProducts.push(item.name);
            }
        }
        return selectedProducts;
    }

    getValidationMessage() {
        const controlErrors: ValidationErrors = this.cableLengthForm.get('cableLength').errors;
        let someError = null;
        if (controlErrors != null) {
            for (const controlError in controlErrors) {
                if (controlErrors[controlError]) {
                    someError = this.validationMessages.find((valMsg) => {
                        return valMsg.type === controlError;
                    });
                    break;
                }
            }
        }
        return someError;
    }

    goBack() {
        this.location.back();
    }
}
