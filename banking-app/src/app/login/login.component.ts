import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BankingService, LoginRequest, Customer } from '../banking.service';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {AsyncPipe} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from "@angular/material/autocomplete";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    imports: [
        AsyncPipe,
        FormsModule,
        MatAutocomplete,
        MatAutocompleteTrigger,
        MatButton,
        MatFormField,
        MatInput,
        MatLabel,
        MatOption
    ],
    standalone: true
})
export class LoginComponent {
    email: string = '';
    password: string = '';
    errorMessage: string = '';

    constructor(private bankingService: BankingService, private router: Router) {}

    login() {
        const loginRequest: LoginRequest = { email: this.email, password: this.password };
        this.bankingService.login(loginRequest).subscribe({
            next: (customer: Customer) => {
                localStorage.setItem('customerId', customer.id); // Save the customer ID for later use
                this.router.navigate(['/accounts']);
            },
            error: () => {
                this.errorMessage = 'Invalid email or password. Please try again.';
            }
        });
    }
}
