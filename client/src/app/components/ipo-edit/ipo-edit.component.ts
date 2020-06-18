import { Component, OnInit, Input, EventEmitter,Output, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
// import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login-service';
import { StockExchangeService } from 'src/app/service/stockexchange.service';
import { IPOService } from 'src/app/service/ipo.service';
import { IPONewOrUpdate } from 'src/app/model/ipo.model';
import * as moment from 'moment';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'ipo-edit',
  templateUrl: './ipo-edit.component.html',
  styleUrls: ['./ipo-edit.component.scss'],
})
export class IPOEditComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

    pushRightClass: string = 'push-right';
    _userRole:string;
    
    isValid: boolean=true;
    showAdminMenu: boolean;
    isOpenAction: boolean;
    isOpenAdminAction: boolean;
    isActive: boolean = false;
    currentPage: string;
    userName: string;
    passCode: string;

    confirmpassCode: string;
    email: string;
    emailFormControl = new FormControl('', [
        Validators.required,
        Validators.email,
      ]);
    
    matcher = new MyErrorStateMatcher();

    stockexchanges=[
      // {displayName:"NSE",value:"NSE"},
      // {displayName:"BYX",value:"BYX"}
    ]
    companies=[];
    burgerKing:HTMLElement;

    @Input() ipoData:any; // 
    @Input() userRole:any; // 
    @Input() mode:string;
    @Output() goback: EventEmitter<any> = new EventEmitter();

    constructor(public elementRef: ElementRef,
        // public userService: UserService,
        public loginService: LoginService,
        private ipoService: IPOService,
        private stockExchangeService: StockExchangeService,
        private router: Router
        ) { 
        
    }

    ngOnDestroy(): void {
        
    }

    ngAfterViewInit() {
  
    }
    ngAfterContentInit() {

    }
    ngOnInit() {     
      console.log("**** IPOEditComponent ngOnInit, this.ipoData: ", this.ipoData);
      this.stockExchangeService.listAllStockExchange().subscribe((response: any) => {
        if(response.code == 200 && response.data && response.data.length > 0) {
          this.stockexchanges = response.data;
        }
      });
      this.loadCompaniesByStockExchangeId();
    }
    
    private loadCompaniesByStockExchangeId() {
      console.log("***** loadCompaniesByStockExchangeId, stockExchangeId: ", this.ipoData.stockchange);
      if(this.ipoData && this.ipoData.stockchange && this.ipoData.stockchange !== '') {
        this.stockExchangeService.listCompaniesByStockExchangeId(this.ipoData.stockchange).subscribe((response: any) => {
          if(response.code == 200 && response.data && response.data.length > 0) {
            this.companies = response.data;
          }
        });
      }
    }

    stockExchangeSelectChanged() {
      console.log("***** stockExchangeSelectChanged, stockExchangeId: ", this.ipoData.stockchange);
      this.loadCompaniesByStockExchangeId();
    }

    Save(){            
      if(this.ipoData.id) { // update
        let ipoUpdate: IPONewOrUpdate = {
          companyId: this.ipoData.companyname,
          stockexchangeId: this.ipoData.stockchange,
          pricePerShare: this.ipoData.price,
          totalShares: this.ipoData.numberofshare,
          openDatetimeStr: moment(this.ipoData.opendatetime).format('YYYY-MM-DD HH:mm:ss'),
          remarks: this.ipoData.remarks
        };
        console.log("****** ipoUpdate and id: ", ipoUpdate, this.ipoData.id);
        this.ipoService.updateIPO(this.ipoData.id, ipoUpdate).subscribe((response: any) => {
          console.log("****** update IPO resp: ", response);
          this.exit(true);
        });
      } else { // new
        let ipoNew: IPONewOrUpdate = {
          companyId: this.ipoData.companyname,
          stockexchangeId: this.ipoData.stockchange,
          pricePerShare: this.ipoData.price,
          totalShares: this.ipoData.numberofshare,
          openDatetimeStr: moment(this.ipoData.opendatetime).format('YYYY-MM-DD HH:mm:ss'),
          remarks: this.ipoData.remarks
        };
        console.log("****** ipoNew: ", ipoNew);
        this.ipoService.createIPO(ipoNew).subscribe((response: any) => {
          console.log("****** create IPO resp: ", response);
          this.exit(true);
        });
      }
    }

    exit(dataChanged){
      // this.goback.emit();
      this.goback.emit({
        dataChanged: dataChanged
      });
    }
}
