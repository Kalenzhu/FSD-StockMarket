import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { tap, delay } from 'rxjs/operators';
import { environment } from "../../environments/environment";
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth-service';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

    constructor(private http: HttpClient,
        private authService: AuthService) { 

    }

    register(sigupDetail: any):Observable<any>{
        this.authService.setToken('')
        const url = `${environment.getBaseUrl('auth')}/signup`;
        return this.http.post<any>(url, {
            'username': sigupDetail.username,
            'password': sigupDetail.password,
            'email': sigupDetail.email,
            'mobileNumber': sigupDetail.mobileNumber
        });
    }
}