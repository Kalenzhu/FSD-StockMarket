import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  //  getAuthorizationToken() {
  //    return localStorage.getItem('JWT-Token');
  //  }

    private name:string = 'jwt-token'
    
    getToken():string {
    　return localStorage.getItem(this.name)
    }
    setToken(token:string):void{
    　localStorage.setItem(this.name, token)
    }
    revokeToken():void {
      localStorage.removeItem(this.name);
    }
    hasToken(): boolean {
      return this.getToken() ? true : false;
    }
}