import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {LoginRequest} from "./banking.service";

export interface ResetPasswordRequest
{
    email: string;
}

export interface ResetPasswordCompleteRequest
{
    email: string;
    otp: string;
    token: string;
    newPassword: string;
}

@Injectable({
    providedIn: 'root'
})
export class ResetPasswordService 
{
    private baseUrl = 'http://localhost:8080'; // Change this to match your backend URL

    constructor(private http: HttpClient) {}

    requestResetPassword(request: ResetPasswordRequest): Observable<{}> {
        return this.http.post<{}>(`${this.baseUrl}/password-reset/request`, request);
    }

    resetPassword(request: ResetPasswordCompleteRequest): Observable<{}> {
        return this.http.post<{}>(`${this.baseUrl}/password-reset`, request);
    }
}