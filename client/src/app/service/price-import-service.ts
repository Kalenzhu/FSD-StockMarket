import { Injectable, Output } from '@angular/core';
import { Observable, Subscriber, Subscription, Subject, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class PriceImportService {
  
    constructor(private http : HttpClient) { 
  
    }

    public upload(fileData:any): Observable<any> {
        const url = `${environment.getBaseUrl('upload')}/api/upload/admin/file`;
        return this.http.post(url, fileData);
    }

}