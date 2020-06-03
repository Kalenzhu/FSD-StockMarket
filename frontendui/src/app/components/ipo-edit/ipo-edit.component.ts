import { Component, OnInit, Input, EventEmitter,Output, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
// import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login-service';
import { StockExchangeService } from 'src/app/service/stockexchange.service';
import { IPOService } from 'src/app/service/ipo.service';
import { IPONewOrUpdate } from 'src/app/model/ipo.model';

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
      this.stockExchangeService.listAllStockExchange().subscribe((response: any) => {
        if(response.code == 200 && response.data && response.data.length > 0) {
          this.stockexchanges = response.data;
        }
      });
    }
    
    Save(){            
      if(this.ipoData.id) { // update
        // let companyUpdate: CompanyUpdate = {
        //   companyName: this.companyEntry.companyname,
        //   turnover: this.companyEntry.turnover,
        //   ceo: this.companyEntry.ceoname,
        //   boardDirectors: this.companyEntry.boardDirectors,
        //   briefWriteUp: this.companyEntry.briefWriteUp,
        //   picUrl: '',
        //   sectorId: this.companyEntry.sector
        // };
        // this.ipoService.updateCompany(this.companyEntry.id, companyUpdate).subscribe((response: any) => {
        //   console.log("****** update company resp: ", response);
        //   this.exit(true);
        // });
      } else { // new
        let ipoNew: IPONewOrUpdate = {
          companyId: this.ipoData.companyname,
          stockexchangeId: this.ipoData.stockchange,
          pricePerShare: this.ipoData.price,
          totalShares: this.ipoData.numberofshare,
          openDatetimeStr: this.ipoData.opendatetime,
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
