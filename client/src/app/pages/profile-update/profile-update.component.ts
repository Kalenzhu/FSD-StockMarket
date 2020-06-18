import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/service/profile-service';
import { LoginService } from 'src/app/service/login-service';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'profile-update',
  templateUrl: './profile-update.component.html',
  styleUrls: ['./profile-update.component.scss'],
})
export class ProfileUpdateComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

    pushRightClass: string = 'push-right';
    _userRole:string;

    isValid: boolean=true;
    showAdminMenu: boolean;
    isOpenAction: boolean;
    isOpenAdminAction: boolean;
    isActive: boolean = false;
    currentPage: string;
    // userName: string;
    passCode: string;
    newPassCode: string;
    confirmpassCode: string;
    // email: string;
    // mobileNumber: string;
    // usernameFormControl = new FormControl('', [
    //     Validators.required
    // ]);
    passwordFormControl = new FormControl('', [
        Validators.required,
        Validators.minLength(6)
    ]);
    newPasswordFormControl = new FormControl('', [
        Validators.required,
        Validators.minLength(6)
    ]);
    confirmPasswordFormControl = new FormControl('', [
        Validators.required,
        Validators.minLength(6)
    ]);
    // emailFormControl = new FormControl('', [
    //     Validators.required,
    //     Validators.email
    // ]);
    // mobileFormControl = new FormControl('', [
    //     Validators.required,
    //     Validators.minLength(8)
    // ]);
    updateMessage = '';

    matcher = new MyErrorStateMatcher();

    burgerKing:HTMLElement;

    constructor(
        public elementRef: ElementRef,
        public userService: UserService,
        private profileService: ProfileService,
        private loginService: LoginService,
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
      this.updateMessage = '';
      if(this.newPassCode !== this.confirmpassCode) {
        this.updateMessage = 'Confirm password incorrect';
        return;
      }

      this.profileService.updatePassword(this.loginService.currentUser, this.passCode, this.newPassCode).subscribe(res=>{
        this.updateMessage = res.msg;
        //redirect to login page
        setTimeout(() => {
            this.loginService.logout();
            this.router.navigateByUrl('/login');
        }, 3000)
      },
      err => {
        this.updateMessage = err.error && err.error.message ? err.error.message : 'Update falied';
      });
    }

    cancel(){
      this.passCode="";
      this.newPassCode="";
      this.confirmpassCode="";
    //   this.email="";
    //   this.mobileNumber="";
    }
   
}
