import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddoreditComponent } from './adminboard/addoredit/addoredit.component';
import { AdminboardComponent } from './adminboard/adminboard.component';
import { ChangepasswordComponent } from './changepassword/changepassword.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuardService } from './services/auth-guard';
import { ShoppingboardComponent } from './shoppingboard/shoppingboard.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'products', component: ShoppingboardComponent,canActivate:[AuthGuardService] },
  { path: 'changepassword', component: ChangepasswordComponent,canActivate:[AuthGuardService]},
  { path: 'admin', component: AdminboardComponent ,canActivate:[AuthGuardService],data: { role:'ROLE_ADMIN' }},
  { path: 'admin/addoredit/:id', component: AddoreditComponent ,canActivate:[AuthGuardService],data: { role:'ROLE_ADMIN' } },
  { path: '', redirectTo: 'products', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
