import {AuthService} from '../service/auth-service';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { mergeMap, catchError } from 'rxjs/operators';
import { of, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler){
    // Get the auth token from the service.
    const authToken = this.authService.getToken();
    console.log('***** AuthInterceptor, Request: '+req.url+', token: ', authToken)
    // Clone the request and replace the original headers with
    // cloned headers, updated with the authorization.
    if (authToken) {
        req = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${authToken}`)
        });
    }
    // send cloned request with header to the next handler.
    return next.handle(req).pipe( 
      mergeMap((event: any) => {
        // if (event instanceof HttpResponse && event.status === 200){
            return this.handleData(event);
            // return of(event);
        // }
      }),
      catchError((err: HttpErrorResponse) => this.handleData(err)));

    // return next.handle(req);
  }

  private handleData(event: HttpResponse<any> | HttpErrorResponse): Observable<any> {
    if(event.status && event.url) { // ignore cors request print
      console.log("***** AuthInterceptor, response "+event.status+", "+event.url);
    }
    switch (event.status) {
      case 200:
      //   if (event instanceof HttpResponse) {
      //       const body: any = event.body;
      //       if (body && body.rc == 3) {
      //         this.router.navigate(['/home']);
      //       }
      //   }
        return of(event);
      case 401: 
      case 403: 
        console.log("***** AuthInterceptor, redirect to login...");
        // this.authService.revokeToken();
        // console.log("*** AuthService remove local useless token and rediret to /login");
        this.router.navigate(['/login']);
        return throwError('errorCode: '+event.status);
      // case 404:
      // break;
      // case 500:
      // break;
      default:
        return of(event);
    }
  }
}