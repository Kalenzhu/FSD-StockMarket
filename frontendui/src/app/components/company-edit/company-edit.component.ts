import { Component, OnInit, Input, EventEmitter,Output, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
// import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { CompanyService } from 'src/app/service/company.service';
import { CompanyNew, CompanyUpdate } from 'src/app/model/company.model';
import { LoginService } from 'src/app/service/login-service';
import { SectorService } from 'src/app/service/sector.service';
import { StockExchangeService } from 'src/app/service/stockexchange.service';
/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'company-edit',
  templateUrl: './company-edit.component.html',
  styleUrls: ['./company-edit.component.scss'],
})
export class CompanyEditComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

    pushRightClass: string = 'push-right';
    _userRole:string;
    
    //companyEntry: any= {companyname: "Name1", ceoname : 'Hydrogen', turnover: 10000, description: 'OEMs in outomotive industry',ipodata:"",sector:"automotive",stockchangename:"",  code:"12345", action:""};
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

    // private _ibmheader: Header;
    // sectors=[
    //   {displayName:"Public",value:"Public"},
    //   {displayName:"Communication",value:"Communication"},
    //   {displayName:"Distribution",value:"Distribution"},
    //   {displayName:"Industria",value:"Industria"},
    //   {displayName:"Commencial",value:"Commencial"},
    //   {displayName:"Enterprise",value:"Enterprise"}
    // ]
    sectors=[];
    stockexchanges=[];
    secondStockchange:any;
    secondCompanyCode:any;
    showSecondStockExchange:boolean = false;
    burgerKing:HTMLElement;
    //home:any[]=["/dashboard"];
    @Input() companyEntry:any; // 
    @Input() userRole:any; // 
    @Output() goback: EventEmitter<any> = new EventEmitter();
    @Input() mode:string;

    constructor(
        public elementRef: ElementRef,
        // public userService: UserService,
        public loginService: LoginService,
        private companyService: CompanyService,
        private sectorService: SectorService,
        private stockExchangeService: StockExchangeService,
        private router: Router
        ) { 
        // router.events.subscribe(val => {
        //     if (
        //         val instanceof NavigationEnd &&
        //         window.innerWidth <= 992 &&
        //         this.isToggled()
        //     ) {
        //         this.toggleSidebar();
        //     }
        // });
        
    }

    ngOnDestroy(): void {
        
    }

    ngAfterViewInit() {
  
    }
    ngAfterContentInit() {

    }
    ngOnInit() {     
      this.sectorService.listAll().subscribe((response: any) => {
        if(response.code == 200 && response.data && response.data.length > 0) {
          // console.log("***** listAllSectors response: ", response);
          this.sectors = response.data;
        }
      });
      this.stockExchangeService.listAllStockExchange().subscribe((response: any) => {
        if(response.code == 200 && response.data && response.data.length > 0) {
          // console.log("***** listAllStockExchange response: ", response);
          this.stockexchanges = response.data;
        }
      });
    }
    
    Save(){            
      if(this.companyEntry.id) { // update
        let companyUpdate: CompanyUpdate = {
          companyName: this.companyEntry.companyname,
          turnover: this.companyEntry.turnover,
          ceo: this.companyEntry.ceoname,
          boardDirectors: this.companyEntry.boardDirectors,
          briefWriteUp: this.companyEntry.briefWriteUp,
          picUrl: '',
          sectorId: this.companyEntry.sector
        };
        this.companyService.updateCompany(this.companyEntry.id, companyUpdate).subscribe((response: any) => {
          console.log("****** update company resp: ", response);
          this.exit(true);
        });
      } else { // new
        let companyNew: CompanyNew = {
          companyName: this.companyEntry.companyname,
          turnover: this.companyEntry.turnover,
          ceo: this.companyEntry.ceoname,
          boardDirectors: this.companyEntry.boardDirectors,
          briefWriteUp: this.companyEntry.briefWriteUp,
          picUrl: '',
          sectorId: this.companyEntry.sector,
          companyStockExchangeList: [{
            stockExchangeId: this.companyEntry.stockchangename,
            companyCode: this.companyEntry.code
          }]
        };
        if(this.secondStockchange && this.secondCompanyCode) {
          companyNew.companyStockExchangeList.push({
            stockExchangeId: this.secondStockchange,
            companyCode: this.secondCompanyCode
          });
        }
        console.log("****** companyNew: ", companyNew);
        this.companyService.createCompany(companyNew).subscribe((response: any) => {
          console.log("****** create company resp: ", response);
          this.exit(true);
        });
      }
      
    }

    exit(dataChanged){
      this.goback.emit({
        dataChanged: dataChanged
      });
    }
   
    addStockExchange(){
      this.showSecondStockExchange = true;
    }

    removeStockExchange(){
      this.showSecondStockExchange = false;
      this.secondStockchange = null;
      this.secondCompanyCode = null;
    }
}
