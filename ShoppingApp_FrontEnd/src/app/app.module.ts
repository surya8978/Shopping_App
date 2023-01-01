import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ShoppingboardComponent } from './shoppingboard/shoppingboard.component';
import { AdminboardComponent } from './adminboard/adminboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ChangepasswordComponent } from './changepassword/changepassword.component';
import { AddoreditComponent } from './adminboard/addoredit/addoredit.component';
import { AuthGuardService } from './services/auth-guard';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    LoginComponent,
    RegisterComponent,
    ShoppingboardComponent,
    AdminboardComponent,
    ChangepasswordComponent,
    AddoreditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
