import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login-service';
import { AuthService } from 'src/app/service/auth-service';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'user-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

    pushRightClass: string = 'push-right';
    _userRole:string;
    
    public programs;
    isValid: boolean=true;
    showAdminMenu: boolean;
    isOpenAction: boolean;
    isOpenAdminAction: boolean;
    isActive: boolean = false;
    currentPage: string;
    userName: string;
    passCode: string;
    emailFormControl = new FormControl('', [
        Validators.required,
        Validators.email,
      ]);
    
    matcher = new MyErrorStateMatcher();

    // @ViewChildren("ibmheader") list:QueryList<ElementRef>;
    // burgerKing:HTMLElement;

    pageMessage: string = '';

    constructor(public elementRef: ElementRef,
        public userService: UserService,
        private loginService: LoginService,
        private authService: AuthService,
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
    //   let checkuser=this.userService.login(this.userName,this.passCode);
    //   if(checkuser===true){
    //     this.isValid=true
    //     this.router.navigate(['/home']);          
    //     //redirect landing page
    //   }
    //   else{
    //     this.isValid=false
    //     //show user not valid
    //   }
    // }

    submit(){
      this.loginService.login(this.userName,this.passCode).subscribe(res=>{
        console.log("*** login response: ", res);
        console.log("*** login jwtToken: ", res.data.jwtToken);
        if(res.code === 200){
          this.loginService.isLoggedIn = true;
          this.loginService.role = res.data.usertype.replace('ROLE_','');
          this.loginService.currentUser = res.data.username;
          console.log("loginService, isLoggedIn role and currentUser: ", this.loginService.isLoggedIn, this.loginService.role, this.loginService.currentUser);
          if(res.data.jwtToken !== null){
            let jwt_token = res.data.jwtToken;
            jwt_token = jwt_token.replace('Bearer ','')
            this.authService.setToken(jwt_token)
          }
        }
        if(this.loginService.isLoggedIn){
          this.router.navigate(['/home']); 
        }
      },
      err => {
        // console.log("*** login falied err: ", err);
        this.pageMessage = err.error && err.error.message ? err.error.message : 'Login falied';
      })
    }

    signup(){
      this.router.navigate(['/signup']);  
    }
   
}
