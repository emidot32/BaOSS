import {Component, OnInit} from '@angular/core';
import {OffersService} from '../_services/offers.service';
import {Router} from '@angular/router';
import {AuthService} from '../_services/auth.service';
import {ToastrService} from 'ngx-toastr';
import {AnalyticsService} from '../_services/analytics.service';
import {DEFAULT_ERROR_MESSAGE, PRODUCTS} from '../_models/constants';
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
    chartWidth = 920;
    chartHeight = 440;
    // analytic results
    userNumber: number;
    userNumberByProductsGraph: GraphParams;
    usersByDateGraph: GraphParams;
    productsByDateGraph: GraphParams;
    profit: number;
    profitByDateGraph: GraphParams;
    aovs: number[];
    aovsByDateGraph: GraphParams;
    arppu: number;
    arppuByDateGraph: GraphParams;
    clv: number;
    clvByDateGraph: GraphParams;
    profitByProduct: GraphParams;
    profitByProductAndDate: GraphParams;

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
        const startDateStr = this.startDate == null ? '' : this.dateFormatPipe.transform(this.startDate, 'dd-MM-yyyy');
        const endDateStr = this.endDate == null ? '' : this.dateFormatPipe.transform(this.endDate, 'dd-MM-yyyy');
        const step = this.step == null || this.step == '' ? 'day' : this.step;
        if (id == 0) {
            if (this.analyticVariants[id].check) {
                this.analyticsService.getCohortAnalysis(startDateStr, endDateStr, step)
                    .subscribe(data => {
                            this.userNumber = data.userNumber;
                            this.usersByDateGraph = {
                                // tslint:disable-next-line:max-line-length
                                data: [{x: data.usersByDate.x, y: data.usersByDate.y, type: 'scatter', mode: 'lines+markers'}] as Plotly.Data[],
                                layout: {width: this.chartWidth, height: this.chartHeight, title: 'User numbers by date'}
                            } as GraphParams;
                            this.userNumberByProductsGraph = {
                                // tslint:disable-next-line:max-line-length
                                data: [{x: data.userNumberByProducts.x, y: data.userNumberByProducts.y, type: 'bar', text: data.userNumberByProducts.y.map(String), textposition: 'auto'}] as Plotly.Data[],
                                layout: {width: this.chartWidth, height: this.chartHeight, title: 'User numbers by products'}
                            } as GraphParams;
                            this.productsByDateGraph = {
                                data: [
                                    {x: data.productsByDate[0].x, y: data.productsByDate[0].y, type: 'bar', name: 'Mobile Product'},
                                    {x: data.productsByDate[1].x, y: data.productsByDate[1].y, type: 'bar', name: 'Internet Product'},
                                    {x: data.productsByDate[2].x, y: data.productsByDate[2].y, type: 'bar', name: 'DTV Product'},
                                ] as Plotly.Data[],
                                layout: {width: 1060, height: this.chartHeight, title: 'Products by date'}
                            } as GraphParams;
                        },
                        error => {
                            if (error.error.message != null && error.error.message != '') {
                                this.toaster.error(error.error.message);
                            } else if (error.message != null && error.message != '') {
                                this.toaster.error(error.message);
                            } else {
                                this.toaster.error(DEFAULT_ERROR_MESSAGE);
                            }
                        });
            } else {
                this.userNumber = null;
                this.usersByDateGraph = null;
                this.userNumberByProductsGraph = null;
                this.productsByDateGraph = null;
            }
        } else if (id == 1) {
            if (this.analyticVariants[id].check) {
                this.analyticsService.getBusinessMetrics(startDateStr, endDateStr, step)
                    .subscribe(data => {
                            this.profit = data.profit;
                            this.profitByDateGraph = {
                                // tslint:disable-next-line:max-line-length
                                data: [{x: data.profitByDate.x, y: data.profitByDate.y, type: 'scatter', mode: 'lines+markers'}] as Plotly.Data[],
                                layout: {width: this.chartWidth, height: this.chartHeight, title: 'GMV by date'}
                            } as GraphParams;
                            this.aovs = data.aovs;
                            this.aovsByDateGraph = {
                                data: [
                                    {x: data.aovsByDate[0].x, y: data.aovsByDate[0].y, type: 'scatter', mode: 'lines+markers', name: 'AOV NRC'},
                                    {x: data.aovsByDate[1].x, y: data.aovsByDate[1].y, type: 'scatter', mode: 'lines+markers', name: 'AOV MRC'}
                                ] as Plotly.Data[],
                                layout: {width: 1060, height: this.chartHeight, title: 'AOVs by date'}
                            } as GraphParams;
                            this.arppu = data.arppu;
                            this.arppuByDateGraph = {
                                // tslint:disable-next-line:max-line-length
                                data: [{x: data.arppuByDate.x, y: data.arppuByDate.y, type: 'scatter', mode: 'lines+markers'}] as Plotly.Data[],
                                layout: {width: this.chartWidth, height: this.chartHeight, title: 'ARPPU by date'}
                            } as GraphParams;
                            this.clv = data.clv;
                            this.clvByDateGraph = {
                                // tslint:disable-next-line:max-line-length
                                data: [{x: data.clvByDate.x, y: data.clvByDate.y, type: 'scatter', mode: 'lines+markers'}] as Plotly.Data[],
                                layout: {width: this.chartWidth, height: this.chartHeight, title: 'CLV by date'}
                            } as GraphParams;
                            this.profitByProduct = {
                                // tslint:disable-next-line:max-line-length
                                data: [{x: data.profitByProduct.x, y: data.profitByProduct.y, type: 'bar', text: data.profitByProduct.y.map(String), textposition: 'auto'}] as Plotly.Data[],
                                layout: {width: this.chartWidth, height: this.chartHeight, title: 'Profit by products, dollar'}
                            } as GraphParams;
                            this.profitByProductAndDate = {
                                data: [
                                    // tslint:disable-next-line:max-line-length
                                    {x: data.profitByProductAndDate[0].x, y: data.profitByProductAndDate[0].y, type: 'lines+markers', name: 'Mobile Product'},
                                    // tslint:disable-next-line:max-line-length
                                    {x: data.profitByProductAndDate[1].x, y: data.profitByProductAndDate[1].y, type: 'lines+markers', name: 'Internet Product'},
                                    // tslint:disable-next-line:max-line-length
                                    {x: data.profitByProductAndDate[2].x, y: data.profitByProductAndDate[2].y, type: 'lines+markers', name: 'DTV Product'},
                                ] as Plotly.Data[],
                                layout: {width: 1060, height: this.chartHeight, title: 'Profits by products and date, dollar'}
                            } as GraphParams;
                        },
                        error => {
                            if (error.error.message != null && error.error.message != '') {
                                this.toaster.error(error.error.message);
                            } else if (error.message != null && error.message != '') {
                                this.toaster.error(error.message);
                            } else {
                                this.toaster.error(DEFAULT_ERROR_MESSAGE);
                            }
                        });
            } else {
                this.profit = null;
                this.profitByDateGraph = null;
                this.aovs = null;
                this.aovsByDateGraph = null;
                this.arppu = null;
                this.arppuByDateGraph = null;
                this.clv = null;
                this.clvByDateGraph = null;
                this.profitByProduct = null;
                this.profitByProductAndDate = null;
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
