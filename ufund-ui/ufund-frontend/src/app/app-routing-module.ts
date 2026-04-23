import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login';
import { Home } from './home/home';
import { authGuard } from './core/guards/auth-guard';

const routes: Routes = [
  {
    path: 'login', 
    component: LoginComponent
  },
  {
    path: 'admin',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./admin/admin-module').then(m => m.AdminModule)
  },
  {
    path: 'helper',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./helper/helper-module').then(m => m.HelperModule)
    
  },
  { path: 'home',
     component: Home 
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
    redirectTo: '/home',
    pathMatch: 'full' 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }