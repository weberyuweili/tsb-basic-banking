import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {BankingService} from "../banking.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  standalone: true
})
export class HeaderComponent implements OnInit {
  
  isLoggedIn: boolean = false;
  constructor(private bankingService: BankingService, private router: Router){}

  ngOnInit()
  {
    this.isLoggedIn = this.bankingService.isLoggedIn()
  }
  logout()
  {
    if (this.bankingService.isLoggedIn()) {
      localStorage.removeItem('jwtToken');
      this.router.navigate(['/']);
    }
  }
}
