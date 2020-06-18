
import { Injectable, Output } from '@angular/core';
import { Observable, Subscriber, Subscription, Subject, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { IPONewOrUpdate } from '../model/ipo.model';


@Injectable({
  providedIn: 'root'
})
export class IPOService {

  constructor(private http : HttpClient) { 
  }

  public getIPOByPage(pageNum: number = 0, pageSize: number = 10): Observable<any> {
    // return this.http.get('/ipo/list');
    let paramsMap = {
      "page": pageNum + "",
      "size": pageSize + "",
      "sort": "id,desc"
    }
    const url = `${environment.getBaseUrl('ipo')}/api/ipo/page`;
    return this.http.get(url, { params: paramsMap });
  }

  public createIPO(ipo: IPONewOrUpdate): Observable<any> {
    const url = `${environment.getBaseUrl('ipo')}/api/ipo/admin/create`;
    return this.http.post(url, ipo);
  }

  public updateIPO(id, ipo: IPONewOrUpdate): Observable<any> {
    const url = `${environment.getBaseUrl('ipo')}/api/ipo/admin/${id}/update`;
    return this.http.post(url, ipo);
  }

  public deleteIPOByid(id): Observable<any> {
    const url = `${environment.getBaseUrl('ipo')}/api/ipo/admin/${id}/delete`;
    return this.http.get(url);
  }
}
