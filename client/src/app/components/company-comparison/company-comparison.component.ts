import { Component, OnInit, Input, EventEmitter,Output, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import { StockExchangeService } from 'src/app/service/stockexchange.service';
import * as moment from 'moment';
import * as _ from "lodash";
import { forkJoin, Subscription, Observable } from 'rxjs';

export class DateRange {
    fromDateStr: string;
    fromDateFullStr: string;
    endDateStr: string;
    endDateFullStr: string;
    priceList: any[];
    finalPrice: any;
}

@Component({
    selector: 'company-comparison',
    templateUrl: './company-comparison.component.html',
    styleUrls: ['./company-comparison.component.scss'],
})
export class CompanyComparisonComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

    stockexchanges=[];
    companies=[];

    stockexchanges2=[];
    companies2=[];

    companyOrSector = ['Company'];
    peroidicity = ['Week', 'Month'];
    showSecondCompany: boolean = false;
    companyComparison:any = {};

    secondCompanyStockchange:any;
    secondCompany:any;

    barChartOption: any = {
        title: {
            text: 'Stock Price Chart'
        },
        legend: {
            data:[]
        },
        tooltip: {},
        // dataset: {
        //   source: fixedDataset,
        // },
        xAxis:{
            data: []
        },
        yAxis: {},
        series: []
    };
    showChart: boolean = false;

    constructor(
        public elementRef: ElementRef,
        private stockExchangeService: StockExchangeService,
    ) {

    }

    ngOnDestroy(): void {
        
    }

    ngAfterViewInit() {
  
    }
    ngAfterContentInit() {

    }
    ngOnInit() { 
        this.stockExchangeService.listAllStockExchange().subscribe((response: any) => {
            if(response.code == 200 && response.data && response.data.length > 0) {
              this.stockexchanges = response.data;
              this.stockexchanges2 = response.data;
            }
        });  
        this.loadCompaniesByStockExchangeId();
    }

    private loadCompaniesByStockExchangeId(isSecond : boolean = false) {
        if(!isSecond) {
            if(this.companyComparison.stockchange) { // First Company
                this.stockExchangeService.listCompaniesByStockExchangeId(this.companyComparison.stockchange).subscribe((response: any) => {
                  if(response.code == 200 && response.data && response.data.length > 0) {
                    this.companies = response.data;
                  }
                });
            }
        } else { 
            if(this.secondCompanyStockchange) { // Second Company
                this.stockExchangeService.listCompaniesByStockExchangeId(this.secondCompanyStockchange).subscribe((response: any) => {
                  if(response.code == 200 && response.data && response.data.length > 0) {
                    this.companies2 = response.data;
                  }
                });
            }
        }
    }
  
    stockExchangeSelectChanged1() {
        this.loadCompaniesByStockExchangeId();
    }

    stockExchangeSelectChanged2() {
        this.loadCompaniesByStockExchangeId(true);
    }
    
    addOrRemoveSecondCompany() {
        this.showSecondCompany = !this.showSecondCompany;
        this.secondCompany = null;
        this.secondCompanyStockchange = null;
    }

    generateMap() {
        let fromDate = moment(this.companyComparison.fromDate);
        let endDate = moment(this.companyComparison.endDate);
        if(fromDate.isAfter(endDate)) {
            console.log("***** generateMap, invalid: from date must before end date");
            return;
        }
        let dateRanges = this.companyComparison.peroidicity ? 
            (
                this.companyComparison.peroidicity == 'Week' ? 
                this.generateWeeksRange(fromDate, endDate) : this.generateMonthsRange(fromDate, endDate)
            ) : [];
        console.log("***** generateMap, dateRanges: ", dateRanges);

        // create tasks and merge task result with date range
        this.createGetPriceTasks(fromDate, endDate).then((resultMap: any) => {
            let dateRangeMap = this.mergePricesWithDateRange(dateRanges, resultMap);
            // generate the bar chart
            this.refreshChartOption(dateRangeMap);
        });
    }

    private createGetPriceTasks(fromDate, endDate) : Promise<any> {
        let tasks:any[] = [];
        let taskNames:any[] = [];

        if(this.companyComparison.company && this.companyComparison.stockchange) {
            taskNames.push(this.companyComparison.company.companyName);
            tasks.push(this.stockExchangeService.getPriceListBetweenDate(
                this.companyComparison.company.id,
                this.companyComparison.stockchange,
                fromDate.format("YYYY-MM-DDTHH:mm:ss"),
                endDate.format("YYYY-MM-DDTHH:mm:ss")
            ));

        }

        if(this.secondCompany && this.secondCompanyStockchange) {
            taskNames.push(this.secondCompany.companyName);
            tasks.push(this.stockExchangeService.getPriceListBetweenDate(
                this.secondCompany.id,
                this.secondCompanyStockchange,
                fromDate.format("YYYY-MM-DDTHH:mm:ss"),
                endDate.format("YYYY-MM-DDTHH:mm:ss")
            ));
        }

        if(tasks.length == 0) return new Promise((resolve, reject) => { resolve({})});

        return forkJoin(tasks).toPromise().then((resp: any) => {
            let totalResultMap: any = {};
            _.forEach(taskNames, (taskName, index) => {
                let taskResp = resp[index];
                if(taskResp.code == 200 && taskResp.data && taskResp.data.length > 0) {
                    totalResultMap[taskName] = taskResp.data;
                }
            });
            // console.log("***** createGetPriceTasks, totalResultMap: ", totalResultMap);
            return totalResultMap;
        });
    }

    private mergePricesWithDateRange(dateRanges, priceListMap) {
        let dateRangeMap = {};
        // traverse task price list result
        Object.keys(priceListMap).forEach(key => {
            let priceList = priceListMap[key];
            let dateRangesCopy = _.cloneDeep(dateRanges);

            _.forEach(priceList, (price) => {
                _.forEach(dateRangesCopy, (dateRange) => {
                    let priceDate = moment(price.priceDate + ' '+ price.priceTime, "YYYY-MM-DD HH:mm:ss");
                    if(priceDate.isBetween(dateRange.fromDateFullStr, dateRange.endDateFullStr)) {
                        dateRange.priceList.push(price);
                    }
                });
            });
            this.calcFinalPriceForEachDateRange(dateRangesCopy);
            dateRangeMap[key] = dateRangesCopy;
        });
        return dateRangeMap;
    }

    private calcFinalPriceForEachDateRange(dateRanges) {
        _.forEach(dateRanges, (dateRange) => {
            dateRange.finalPrice = _.maxBy(dateRange.priceList, 'currentPrice');
        });
    }

    private refreshChartOption(dateRangeMap) {
        // clear char option first
        this.clearChartOption();
        this.showChart = true;

        let xAxisInit = false;
        Object.keys(dateRangeMap).forEach(key => {
            let dateRanges = dateRangeMap[key];
            if(!xAxisInit) {
                this.barChartOption.xAxis.data = _.map(dateRanges, (dateRange) => dateRange.fromDateStr+"-"+dateRange.endDateStr);
            }
            this.barChartOption.legend.data.push(key);
            this.barChartOption.series.push({
                name: key,
                type: 'bar',
                markLine: {
					data: [{
						name: 'average',
						type: 'average'
					},{
						name: 'max',
						type: 'max'
					},{
						name: 'min',
						type: 'min'
					}]
				},
                data: _.map(dateRanges, (dateRange) => dateRange.finalPrice ? dateRange.finalPrice.currentPrice : 0)
            });
        });
    }

    private clearChartOption() {
        this.barChartOption.legend.data = [];
        this.barChartOption.xAxis.data = [];
        this.barChartOption.series = [];
    }

    private generateWeeksRange(fromDate, endDate) {
        let dateRanges:any = [];
        let diffWeeks = endDate.diff(fromDate, 'weeks');
        let m = _.cloneDeep(fromDate);
        while(diffWeeks > 0) {
            let curWeek = _.cloneDeep(m);
            let nextWeek = m.add(1, 'weeks');
            // console.log("***** generateMap, curWeek and nextWeek, curWeek: "+curWeek.format('MMM DD')+", nextWeek: "+nextWeek.format('MMM DD'));
            if(nextWeek.isAfter(endDate)) {
                nextWeek = _.cloneDeep(endDate);
            }
            let weekRange: DateRange = {
                fromDateStr: curWeek.format('MMM DD'),
                fromDateFullStr: curWeek.format('YYYY-MM-DD HH:mm:ss'),
                endDateStr: nextWeek.format('MMM DD'),
                endDateFullStr: nextWeek.format('YYYY-MM-DD HH:mm:ss'),
                priceList: [],
                finalPrice: {}
            }
            dateRanges.push(weekRange);
            m = _.cloneDeep(nextWeek);
            diffWeeks--;
        }
        if(m.isBefore(endDate)) {
            let weekRange: DateRange = {
                fromDateStr: m.format('MMM DD'),
                fromDateFullStr: m.format('YYYY-MM-DD HH:mm:ss'),
                endDateStr: endDate.format('MMM DD'),
                endDateFullStr: endDate.format('YYYY-MM-DD HH:mm:ss'),
                priceList: [],
                finalPrice: {}
            }
            dateRanges.push(weekRange);
        }
        return dateRanges;
    }

    private generateMonthsRange(fromDate, endDate) {
        let dateRanges:any = [];
        let diffMonths = endDate.diff(fromDate, 'months');
        let m = _.cloneDeep(fromDate);
        while(diffMonths > 0) {
            let curMonth = _.cloneDeep(m);
            let nextMonth = m.add(1, 'months');
            if(nextMonth.isAfter(endDate)) {
                nextMonth = _.cloneDeep(endDate);
            }
            let monthRange: DateRange = {
                fromDateStr: curMonth.format('YYYY/MM'),
                fromDateFullStr: curMonth.format('YYYY-MM-DD HH:mm:ss'),
                endDateStr: nextMonth.format('YYYY/MM'),
                endDateFullStr: nextMonth.format('YYYY-MM-DD HH:mm:ss'),
                priceList: [],
                finalPrice: {}
            }
            dateRanges.push(monthRange);
            m = _.cloneDeep(nextMonth);
            diffMonths--;
        }
        if(m.isBefore(endDate)) {
            let monthRange: DateRange = {
                fromDateStr: m.format('YYYY/MM'),
                fromDateFullStr: m.format('YYYY-MM-DD HH:mm:ss'),
                endDateStr: endDate.format('YYYY/MM'),
                endDateFullStr: endDate.format('YYYY-MM-DD HH:mm:ss'),
                priceList: [],
                finalPrice: {}
            }
            dateRanges.push(monthRange);
        }
        return dateRanges;
    }

    // autoCompleteFilter1() {
    //     if(this.companies && this.companyComparison && this.companyComparison.companyName) {
    //         const filterValue = this.companyComparison.companyName.toLowerCase();
    //         return this.companies.filter(option => option.companyName && option.companyName.toLowerCase().indexOf(filterValue) === 0);
    //     }
    //     return this.companies;
    // }

    // autoCompleteFilter2() {
    //     if(this.companies2 && this.secondCompany) {
    //         const filterValue = this.secondCompany.toLowerCase();
    //         return this.companies2.filter(option => option.companyName && option.companyName.toLowerCase().indexOf(filterValue) === 0);
    //     }
    //     return this.companies2;
    // }
}