import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
// import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
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
    
    matcher = new MyErrorStateMatcher();

    constructor(
        public elementRef: ElementRef,
        // public userService: UserService,
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

    }

    cancel(){
      this.isValid=true
    }

    signup(){
      this.router.navigate(['/signup']);  
    }
   
}
