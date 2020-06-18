
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

  public listCompaniesByStockExchangeId(stockExchangeId): Observable<any> {
    const url = `${environment.getBaseUrl('stockExchange')}/api/stockexchange/${stockExchangeId}/listCompaniesById`;
    return this.http.get(url);
  }

  public listCompaniesByStockExchangeName(stockExchangeName): Observable<any> {
    const url = `${environment.getBaseUrl('stockExchange')}/api/stockexchange/${stockExchangeName}/listCompaniesByName`;
    return this.http.get(url);
  }

  public getPriceListBetweenDate(companyId, stockExchangeId, startDateStr, endDateStr): Observable<any> {
    const url = `${environment.getBaseUrl('stockExchange')}/api/stockprice/${companyId}/stockexchange/${stockExchangeId}/list`;
    return this.http.get(url, { 
      params: {
        "startDateStr": startDateStr,
        "endDateStr": endDateStr
      }
    });
  }

  public getLatestStockPrice(companyId, stockExchangeId): Observable<any> {
    const url = `${environment.getBaseUrl('stockExchange')}/api/stockprice/${companyId}/stockexchange/${stockExchangeId}/latest`;
    return this.http.get(url);
  }
}
