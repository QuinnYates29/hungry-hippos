import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HelperDashboard } from './pages/helper-dashboard/helper-dashboard';
import { HelperRoutingModule } from './helper-routing-module';
import { HelperSearch } from './pages/helper-search/helper-search';


@NgModule({
  declarations: [
    HelperDashboard,
    HelperSearch
  ],
  imports: [
    CommonModule,
    HelperRoutingModule
  ],
})
export class HelperModule { }
