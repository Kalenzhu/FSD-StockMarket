import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth-service';

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient) { }

    updatePassword(username, password, newPassword) : Observable<any>{
        const url = `${environment.getBaseUrl('auth')}/settings`;
        return this.http.post<any>(url, {
            'username': username,
            'password': password,
            'newPassword': newPassword
        });
    }
}