import { NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login';
import { Home } from './home/home';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    Home
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
