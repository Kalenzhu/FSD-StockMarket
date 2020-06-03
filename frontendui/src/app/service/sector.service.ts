
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SectorService {

  constructor(private http : HttpClient) { 

  }

  public listAll(): Observable<any> {
    const url = `${environment.getBaseUrl('sector')}/api/sector/list`;
    return this.http.get(url);
  }
}
