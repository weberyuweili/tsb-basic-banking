import { Component } from '@angular/core';
import {BankingService, Account, Transaction} from '../banking.service';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import { CommonModule } from '@angular/common';
import {TransferComponent} from "../transfer/transfer.component";

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    TransferComponent
  ],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent {
  accounts: Account[] = [];
  selectedAccount: string = '';
  transactions: Transaction[] = [];
  errorMessage: string = '';
  transferring: boolean = false;
  constructor(private bankingService: BankingService) {}

  ngOnInit() {
    const customerId = localStorage.getItem('customerId');
    if (customerId) {
      this.bankingService.getAccounts(customerId).subscribe({
        next: (accounts) => {
          console.log(accounts);
          this.accounts = accounts;
        },
        error: () => {
          this.errorMessage = 'Failed to fetch accounts. Please try again later.';
        }
      });
    }
  }

  viewAccountDetails(account: { accountNumber: string }): void {
    this.transferring = false;
    var accountNumber = account.accountNumber;
    this.bankingService.getTransactions(accountNumber).subscribe({
      next: (transactions) => {
        console.log(transactions);
        this.transactions = transactions;
      },
      error: () => {
        this.errorMessage = 'Failed to fetch accounts. Please try again later.';
      }
    });
  }

  makeTransfer(account: { accountNumber: string }): void {
    this.transferring = true;
    this.selectedAccount = account.accountNumber;
  }

  onMoneyTransferred(response: string): void {
    this.transferring = false;
    this.ngOnInit();
  }
}
