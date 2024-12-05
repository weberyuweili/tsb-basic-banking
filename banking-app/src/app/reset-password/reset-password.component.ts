import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {ActivatedRoute} from '@angular/router';
import {ResetPasswordService, ResetPasswordRequest, ResetPasswordCompleteRequest} from '../resetpassword.service';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
  imports: [
    CommonModule,
    FormsModule,
    MatFormField,
    MatLabel,
    MatButton,
    MatInput
  ],
  standalone: true
})
export class ResetPasswordComponent implements OnInit {
  email: string = '';
  otp: string = '';
  newPassword: string = '';
  successMessage: string | null = null;
  errorMessage: string | null = null;
  token: string | null = null;
  constructor(private resetPasswordService: ResetPasswordService, private route: ActivatedRoute) {
  }

  ngOnInit(){
    this.route.queryParams.subscribe( params => {
      console.log(params);
      this.token = params['token'];
      console.log(this.token);
    });
  }

  requestReset() {
    const request: ResetPasswordRequest = { email: this.email };
      this.resetPasswordService.requestResetPassword(request).subscribe({
        next: () => {
          this.successMessage = 'Password reset link sent to your email!';
        },
        error: (err) => {
          this.errorMessage = 'Failed to send reset link. Please try again.';
        },
      });
  }

  resetPassword() {
    const tt= this.token != null ? this.token : '';
    const request: ResetPasswordCompleteRequest = { email: this.email, token: tt, otp: this.otp, newPassword: this.newPassword };
    this.resetPasswordService.resetPassword(request).subscribe({
      next: () => {
        this.successMessage = 'Password reset link sent to your email!';
      },
      error: (err) => {
        this.errorMessage = 'Failed to send reset link. Please try again.';
      },
    });
  }

}