import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { SignupService } from 'src/app/service/signup-service';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'user-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

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
    mobileNumber: string;
    usernameFormControl = new FormControl('', [
        Validators.required
    ]);
    passwordFormControl = new FormControl('', [
        Validators.required,
        Validators.minLength(6)
    ]);
    confirmPasswordFormControl = new FormControl('', [
        Validators.required,
        Validators.minLength(6)
    ]);
    emailFormControl = new FormControl('', [
        Validators.required,
        Validators.email
    ]);
    mobileFormControl = new FormControl('', [
        Validators.required,
        Validators.minLength(8)
    ]);
    signupMessage = '';

    matcher = new MyErrorStateMatcher();

    burgerKing:HTMLElement;

    constructor(
        public elementRef: ElementRef,
        public userService: UserService,
        private signupService: SignupService,
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
    
    // submit(){
    //     this.router.navigate(['/login']);          
    // }

    submit(){
      this.signupMessage = '';
      if(this.passCode !== this.confirmpassCode) {
        this.signupMessage = 'Confirm password incorrect';
        return;
      }
      console.log("**** signup test, username, password, email, mobileNumber:", this.userName, this.passCode, this.email, this.mobileNumber);
      this.signupService.register({
        'username': this.userName,
        'password': this.passCode,
        'email': this.email,
        'mobileNumber': this.mobileNumber
      }).subscribe(res=>{
        console.log(res)
        this.signupMessage = res.msg;
        //redirect to login page
        setTimeout(() => {
          this.router.navigateByUrl('/login');
        }, 5000)
      })
    }

    cancel(){
      this.userName="";
      this.passCode="";
      this.confirmpassCode="";
      this.email="";
      this.mobileNumber="";
    }
   
}
