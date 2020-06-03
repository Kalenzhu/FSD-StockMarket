
import { Injectable, Output } from '@angular/core';
import { Observable, Subscriber, Subscription, Subject, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CompanyNew, CompanyUpdate } from '../model/company.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http : HttpClient) { 

  }

  public listCompanies(searchText?: string): Observable<any> {
    let paramsMap = {
      "searchText": searchText ? searchText : ""
    };
    const url = `${environment.getBaseUrl('company')}/api/company/list`;
    return this.http.get(url, { params: paramsMap });
  }

  public listCompaniesByPage(pageNum: number = 0, pageSize: number = 10, searchText?: string): Observable<any> {
    let paramsMap = {
      "page": pageNum + "",
      "size": pageSize + "",
      "sort": "id,desc",
      "searchText": searchText ? searchText : ""
    }
    const url = `${environment.getBaseUrl('company')}/api/company/page`;
    return this.http.get(url, { params: paramsMap });
  }

  public getCompanyByid(id): Observable<any> {
    const url = `${environment.getBaseUrl('company')}/api/company/${id}`;
    return this.http.get(url);
  }

  public createCompany(company: CompanyNew): Observable<any> {
    const url = `${environment.getBaseUrl('company')}/api/company/admin/create`;
    return this.http.post(url, company);
  }

  public updateCompany(id, company: CompanyUpdate): Observable<any> {
    const url = `${environment.getBaseUrl('company')}/api/company/admin/${id}/update`;
    return this.http.post(url, company);
  }

  public deleteCompanyByid(id): Observable<any> {
    const url = `${environment.getBaseUrl('company')}/api/company/admin/${id}/delete`;
    return this.http.get(url);
  }

  // private getAuthHeaders() : any {
  //   var headers = new Headers();
  //   // headers.set('Accept', 'application/json');
  //   headers.set('Content-Type', 'application/json');
  //   headers.set('Authorization', 'bearer ' + this.authService.accessToken);
  //   return headers;
  // }
}
