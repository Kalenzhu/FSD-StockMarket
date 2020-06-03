import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
// import { OAuthService } from 'angular-oauth2-oidc';
// import { Auth2Service } from '../service/auth-service-old';
import { LoginService } from '../service/login-service';
import { AuthService } from '../service/auth-service';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(
        // private oauthService: OAuthService, 
        // private authService: Auth2Service,
        private loginService: LoginService,
        private authService: AuthService,
        private router: Router) {

    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): 
        boolean | import("rxjs").Observable<boolean> | Promise<boolean> {
        // if(!this.loginService.isLoggedIn) {
        if(!this.loginService.isLoggedIn) {
            if(this.authService.hasToken()) { // local storage has token
                return new Promise<boolean>((resolve, reject) => {
                    this.loginService.getCurrentUser().subscribe(res=>{
                        console.log("*** loginService getCurrentUser, response: ", res);
                        if(res.code === 200){
                          this.loginService.isLoggedIn = true;
                          this.loginService.role = res.data.usertype.replace('ROLE_','');
                          this.loginService.currentUser = res.data.username;
                        }
                        resolve(this.loginService.isLoggedIn ? true : false);
                      },
                      err => {
                        console.log("*** loginService getCurrentUser, err: ", err);
                        this.router.navigate(['/login']);
                        // reject(false);
                        resolve(false);
                    });
                });
                
            } else { // local has no token
                console.log("****** AuthGuard, not login, redirect to login");
                this.router.navigate(['/login']);
                return false;
            }
        }
        console.log("****** AuthGuard, already login, allow to enter component");
        return true;
        // return this.authService.identityValid();
        // if (this.oauthService.hasValidAccessToken()) { //hasValidIdToken
        //     console.log("****** AuthGuard.canActivate, oauthService.hasValidAccessToken() == true");
        //     return true;
        // }
        // console.log("****** AuthGuard.canActivate, oauthService.hasValidAccessToken() == false");
        // this.router.navigate(['/']);
        // return false;
    }
}