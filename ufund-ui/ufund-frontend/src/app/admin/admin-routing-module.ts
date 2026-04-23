import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { HipposDashboard } from './pages/hippos-dashboard/hippos-dashboard';

const routes: Routes = [
  { path: 'dashboard', component: Dashboard}, 
  { path: 'hippos-dashboard', component: HipposDashboard },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }