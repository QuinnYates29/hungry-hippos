import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HelperDashboard } from './helper/pages/helper-dashboard/helper-dashboard';

const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () =>
      import('./admin/admin-module').then(m => m.AdminModule)
  },
  {
    path: 'helper',
    loadChildren: () =>
      import('./helper/helper-module').then(m=> m.HelperModule)
  },
  {
    path: '',
    redirectTo: 'helper/helper-dashboard',
    pathMatch: 'full' 
  }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
