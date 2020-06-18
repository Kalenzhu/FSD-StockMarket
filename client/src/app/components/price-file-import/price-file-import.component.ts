import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
// import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { PriceImportService } from 'src/app/service/price-import-service';
import { MatDialog } from '@angular/material/dialog';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'price-file-import',
  templateUrl: './price-file-import.component.html',
  styleUrls: ['./price-file-import.component.scss'],
})
export class PriceFileImportComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

    pushRightClass: string = 'push-right';
    _userRole:string;    
    public programs;
    isValid: boolean=true;
    showAdminMenu: boolean;
    isOpenAction: boolean;
    isOpenAdminAction: boolean;
    isActive: boolean = false;
    currentPage: string;
    filename: string;
    emailFormControl = new FormControl('', [
        Validators.required,
        Validators.email,
    ]);
    summary = {
      companyname: '',
      stockexchange: '',
      numofimport: 0,
      fromdate: '',
      todate: ''
    }
    matcher = new MyErrorStateMatcher();
    @ViewChild('fileinput') private fileinput: ElementRef;
    @ViewChild('content') content: string;

    constructor(
        public elementRef: ElementRef,
        // public userService: UserService,
        private priceImportService: PriceImportService,
        private dialog: MatDialog,
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
    }

    submit(){
      const _file = this.fileinput.nativeElement.files;
      const formData = new FormData();
      let file = _file[0];
      formData.append('uploadFile', file, this.filename)
      console.log("***** submit formData: ", formData)
      this.priceImportService.upload(formData).subscribe(result => {
        console.log("****** upload result: ", result);
        if(result.code == 200 && result.data) {
          this.setSummary(result.data);
          this.open(this.content);
          this.filename = '';
        }
      });
    }

    setSummary(summaryResponse){
      this.summary.companyname = summaryResponse.companyName;
      this.summary.stockexchange = summaryResponse.stockExchange;
      this.summary.numofimport = summaryResponse.totalRecords;
      this.summary.fromdate = summaryResponse.startDateStr;
      this.summary.todate = summaryResponse.endDateStr;
    }

    open(content) {
      this.dialog.open(content, {
        height: '400px',
        width: '600px',
      });
      // const dialogRef = this.dialog.open(content);
      // dialogRef.afterClosed().subscribe(result => {
      //   console.log(`Dialog result: ${result}`);
      // });
    }

    cancel(){
      this.isValid=true
    }
   
}
