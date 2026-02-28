import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HelperDashboard } from './pages/helper-dashboard/helper-dashboard';
import { HelperRoutingModule } from './helper-routing-module';


@NgModule({
  declarations: [
    HelperDashboard
  ],
  imports: [
    CommonModule,
    HelperRoutingModule
  ]
})
export class HelperModule { }
