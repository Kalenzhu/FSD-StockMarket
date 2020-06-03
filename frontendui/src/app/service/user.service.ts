import { Injectable, Output } from '@angular/core';
import { Observable, Subscriber, Subscription, Subject, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public loggedIn: boolean;
  public currentUser: any;

  private testUserList=[
    {userName:"admin",password:"123456",roles:["admin"]},
    {userName:"zuogan",password:"123456",roles:["user"]}
  ]
  public userList = this.testUserList; // []

  constructor(private http : HttpClient) { 
    this.loggedIn = false;
  }

  // login
  public login(userName,passWord){

    for(let user in this.userList){
      if(this.userList[user].userName=== userName && this.userList[user].password=== passWord ){
        this.currentUser=this.userList[user];
       
        this.loggedIn=true;
        return true
      }
    }    
    return false
  }

  public inArray = function(arr, item) {
    for(var i = 0; i < arr.length; i++) {
           if(arr[i] == item) {
             return true;
           }
    }
    return false;
  };

  public getUserRole(){
    if(this.currentUser && this.currentUser.roles && this.currentUser.roles.length>=1){

      if(this.inArray(this.currentUser.roles,'admin')){
        return "admin"
      }
      if(this.inArray(this.currentUser.roles,'user')){
        return "user"
      }
      return ""
    }
  }

  public logout(): Observable<any> {
    this.loggedIn = false;
    this.currentUser = null
    return this.http.get('/logout');
  }
}
