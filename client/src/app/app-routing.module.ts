import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeHeaderComponent } from './components/home-header/home-header.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { AuthGuard } from './guard/auth-guard';
const routes: Routes = [

  // { path: 'header', component: HomeHeaderComponent, canActivate: [ AuthGuard ] },
  // { path: 'header', component: HomeHeaderComponent, canActivate: [ AuthGuard ]},
  { path: 'home', component: HomePageComponent, canActivate: [ AuthGuard ]
    // children: [
    //   {
    //       path: '/flight-search',
    //       component: FlightSearchComponent
    //   }
    // ] 
  },
  { path: 'login', component: LoginComponent},
  { path: 'signup', component: SignupComponent},
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [ AuthGuard ]
})
export class AppRoutingModule { }
