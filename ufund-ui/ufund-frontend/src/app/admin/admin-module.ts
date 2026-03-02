import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing-module';
import { Login } from '../login/login';
import { Dashboard } from './pages/dashboard/dashboard';


@NgModule({
  declarations: [
    Login,
    Dashboard
  ],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
