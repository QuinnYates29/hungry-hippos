import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing-module';
import { Dashboard } from './pages/dashboard/dashboard';
import { FormsModule } from '@angular/forms';
import { HipposDashboard } from './pages/hippos-dashboard/hippos-dashboard';


@NgModule({
  declarations: [
    Dashboard,
    HipposDashboard
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule
  ]
})
export class AdminModule { }
