import {Component, EventEmitter, Input, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { BankingService, TransferRequest } from '../banking.service';
@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {
  @Input()
  accounts: { accountNumber: string; balance: number }[] = [];

  @Input()
  selectedAccount: string = '';
  transferRequest: TransferRequest = {
    toAccountNumber: '',
    amount: 0,
    reference: ''
  };

  @Output()
  customEvent: EventEmitter<string> = new EventEmitter<string>();
  
  constructor(private bankingService: BankingService) {}
  
  transferMoney()
  {
    this.bankingService.transfer(this.selectedAccount, this.transferRequest).subscribe({
      next: () => {
        this.customEvent.emit('Done');
      },
      error: () => {
        
      }
    });
    
  }
}
