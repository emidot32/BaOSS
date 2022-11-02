import {Component, OnInit} from '@angular/core';
import {OffersService} from '../_services/offers.service';
import {Router} from '@angular/router';
import {AuthService} from '../_services/auth.service';
import {ToastrService} from 'ngx-toastr';
import {AnalyticsService} from '../_services/analytics.service';
import {PRODUCTS} from '../_models/constants';
import {ChartData, CohortAnalysis, GraphParams} from '../_models/interface';
import {DatePipe} from '@angular/common';
import {Plotly} from 'angular-plotly.js/src/app/shared/plotly.interface';

@Component({
    selector: 'app-analytics',
    templateUrl: './analytics.component.html',
    styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {
    // parameters
    startDate: Date;
    startDateErrMsg: string;
    endDate: Date;
    endDateErrMsg: string;
    step: string;
    stepVars = ['day', 'week', 'month', 'quarter', 'year'];
    analyticVariants = [
        {id: 0, name: 'Cohort analysis', active: false, check: false},
        {id: 1, name: 'Business metrics', active: false, check: false},
        {id: 2, name: 'Profit forecasting', active: false, check: false}
    ];
    analyticSelected: boolean;
    // analytic results
    userNumber: number;
    userNumberByProductsGraph: GraphParams;
    usersByDateGraph: GraphParams;
    productsByDateGraph: GraphParams;

    constructor(private analyticsService: AnalyticsService,
                private router: Router,
                private authService: AuthService,
                private toaster: ToastrService,
                private dateFormatPipe: DatePipe) {
    }

    ngOnInit() {
    }

    validateStartDate(value: Date) {
        const curDate = new Date();
        if (value != null) {
            if (value >= curDate) {
                this.startDateErrMsg = 'Start date can not be more than today';
                this.startDate = null;
            } else if (this.endDate != null && value > this.endDate) {
                this.startDateErrMsg = 'Start date can not be more than the end date';
                this.startDate = null;
            } else {
                this.startDateErrMsg = null;
                this.startDate = value;
            }
        }
    }

    validateEndDate(value: Date) {
        if (value != null && this.startDate != null && value <= this.startDate) {
            this.endDateErrMsg = 'Start date can not be more than the end date';
        } else {
            this.endDateErrMsg = null;
            this.endDate = value;
        }
    }

    getNeededAnalytics(id: number) {
        console.log(this.userNumberByProductsGraph);
        this.analyticVariants[id].check = !this.analyticVariants[id].check;
        if (id == 0) {
            if (this.analyticVariants[id].check) {
                const startDateStr = this.startDate == null ? '' : this.dateFormatPipe.transform(this.startDate, 'dd-MM-yyyy');
                const endDateStr = this.endDate == null ? '' : this.dateFormatPipe.transform(this.endDate, 'dd-MM-yyyy');
                const step = this.step == null || this.step == '' ? 'day' : this.step;
                this.analyticsService.getUserNumber(startDateStr, endDateStr)
                    .subscribe(data => this.userNumber = data,
                        error => this.toaster.error(error.error.message));
                this.analyticsService.getUserNumbersByProducts(startDateStr, endDateStr)
                    .subscribe(data => this.userNumberByProductsGraph = {
                            data: [{x: data.x, y: data.y, type: 'bar', text: data.y.map(String), textposition: 'auto'}] as Plotly.Data[],
                            layout: {width: 820, height: 440, title: 'User numbers by products'}
                        } as GraphParams,
                        error => this.toaster.error(error.error.message));
                this.analyticsService.getUsersByDate(startDateStr, endDateStr, step)
                    .subscribe(data => this.usersByDateGraph = {
                            data: [{x: data.x, y: data.y, type: 'scatter', mode: 'lines+points'}] as Plotly.Data[],
                            layout: {width: 820, height: 440, title: 'User numbers by date'}
                        } as GraphParams,
                        error => this.toaster.error(error.error.message));
                this.analyticsService.getProductsByDate(startDateStr, endDateStr, step)
                    .subscribe(data => this.productsByDateGraph = {
                            data: [
                                {x: data[0].x, y: data[0].y, type: 'scatter', mode: 'lines+points', label: 'Mobile Product'},
                                {x: data[1].x, y: data[1].y, type: 'scatter', mode: 'lines+points', label: 'Internet Product'},
                                {x: data[2].x, y: data[2].y, type: 'scatter', mode: 'lines+points', label: 'DTV Product'},
                            ] as Plotly.Data[],
                            layout: {width: 820, height: 640, title: 'Products by date'}
                        } as GraphParams,
                        error => this.toaster.error(error.error.message));
            } else {
                this.userNumber = null;
                this.usersByDateGraph = null;
                this.userNumberByProductsGraph = null;
                this.productsByDateGraph = null;
            }
        } else if (id == 1) {
            if (this.analyticVariants[id].check) {

            }
        } else if (id == 2) {
            if (this.analyticVariants[id].check) {

            }
        }
        let anyAnalyticsSelected = false;
        for (const item of this.analyticVariants) {
            if (item.check) {
                anyAnalyticsSelected = true;
            }
        }
        this.analyticSelected = anyAnalyticsSelected;
    }


}
