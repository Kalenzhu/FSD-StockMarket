
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { ComponentsModule } from '../components/components.module';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTabsModule} from '@angular/material/tabs';
import {MatInputModule} from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button'
import {MatFormFieldModule} from '@angular/material/form-field';
import { HomePageComponent } from './home-page/home-page.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ProfileUpdateComponent } from './profile-update/profile-update.component';
@NgModule({
  imports: [ 
    MatSidenavModule,
    MatTabsModule,
    ComponentsModule,
    MatInputModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    FormsModule,
    CommonModule,
    MatButtonModule,
    HttpClientModule
  ],
  declarations: [ 
    HomePageComponent,
    LoginComponent,
    SignupComponent,
    ProfileUpdateComponent
  ],
  exports: [
    HomePageComponent,
    LoginComponent,
    SignupComponent,
    ProfileUpdateComponent  
  ],
  entryComponents: [
  
  ]
})
export class PagesModule { }
