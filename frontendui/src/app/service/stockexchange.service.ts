
import { Injectable, Output } from '@angular/core';
import { Observable, Subscriber, Subscription, Subject, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CompanyNew, CompanyUpdate } from '../model/company.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StockExchangeService {

  constructor(private http : HttpClient) { 

  }

  public listAllStockExchange(): Observable<any> {
    const url = `${environment.getBaseUrl('stockExchange')}/api/stockexchange/list`;
    return this.http.get(url);
  }
}
