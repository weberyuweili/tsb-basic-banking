import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from "./login/login.component";
import { HeaderComponent } from './header/header.component';
import { FooterComponent} from "./footer/footer.component";
import { AccountsComponent } from './accounts/accounts.component';
import { ResetPasswordComponent} from "./reset-password/reset-password.component";

const routes: Routes = [
  {path: '', component: LoginComponent}, // landing
  {path: 'accounts', component: AccountsComponent},
  {path: 'reset-password', component: ResetPasswordComponent},
  {path: 'reset-password/:token', component: ResetPasswordComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes), HeaderComponent, FooterComponent],
  exports: [RouterModule]
})
export class AppRoutingModule { }
