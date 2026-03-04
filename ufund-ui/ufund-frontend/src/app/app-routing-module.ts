import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login';

const routes: Routes = [
  {
    path: 'login', 
    component: LoginComponent
  },
  {
    path: 'admin',
    loadChildren: () =>
      import('./admin/admin-module').then(m => m.AdminModule)
  },
  {
    path: 'helper',
    loadChildren: () =>
      import('./helper/helper-module').then(m => m.HelperModule)
  },

  // TEST ROUTES (can alternate)
  {
    path: 'admin-test',
    redirectTo: 'admin/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'helper-test',
    redirectTo: 'helper/helper-dashboard',
    pathMatch: 'full'
  },

  // DEFAULT ROUTE
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full' 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }