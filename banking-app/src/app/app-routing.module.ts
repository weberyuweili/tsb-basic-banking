import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from "./login/login.component";
import { HeaderComponent } from './header/header.component';
import { FooterComponent} from "./footer/footer.component";
import { AccountsComponent } from './accounts/accounts.component';

const routes: Routes = [
  {path: '', component: LoginComponent}, // landing
  {path: 'accounts', component: AccountsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes), HeaderComponent, FooterComponent],
  exports: [RouterModule]
})
export class AppRoutingModule { }
