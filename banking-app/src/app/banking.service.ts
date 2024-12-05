import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface Account {
  accountNumber: string;
  name: string;
  balance: number;
}

export interface Customer {
  id: string;
  name: string;
  email: string;
}

export interface Transaction {
  description: string;
  amount: string;
  transactionDate: string;
}

export interface TransferRequest
{
  toAccountNumber: string;
  amount: number;
  reference: string;
}

@Injectable({
  providedIn: 'root'
})
export class BankingService {
  private baseUrl = 'http://localhost:8080'; // Change this to match your backend URL

  constructor(private http: HttpClient) {}

  isLoggedIn(): boolean {
    const token = localStorage.getItem('jwtToken');
    return !!token; // Optionally validate the token expiration
  }
  
  login(loginRequest: LoginRequest): Observable<{token: string, customerId: string}> {
    return this.http.post<{token: string, customerId: string}>(`${this.baseUrl}/login`, loginRequest);
  }

  getAccounts(customerId: string): Observable<Account[]> {
    console.log(customerId);
    return this.http.get<Account[]>(`${this.baseUrl}/customer/${customerId}/accounts`);
  }

  getTransactions(accountNumber: string): Observable<Transaction[]> {
    console.log(accountNumber);
    return this.http.get<Transaction[]>(`${this.baseUrl}/accounts/${accountNumber}/transactions`);
  }
  
  transfer(accountNumber: string, request: TransferRequest) {
    return this.http.post<any>(`${this.baseUrl}/accounts/${accountNumber}/transfer`, request);
  }
}
