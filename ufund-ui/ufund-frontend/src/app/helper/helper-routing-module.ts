import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HelperDashboard } from './pages/helper-dashboard/helper-dashboard';

const routes: Routes = [
  {path:'helper-dashboard', component: HelperDashboard}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HelperRoutingModule { }
