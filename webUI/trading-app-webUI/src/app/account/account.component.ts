import { Component, Input, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { Account as Account } from '../models/account';
import { State } from '../models/state';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  @Input() accountInput!:Account

  state = State

  account!:Account

  constructor(private currencyService:CurrencyService) {
    
  }

  create(){
    this.currencyService.accountCreate(this.account);
  }

  update(){
    this.currencyService.accountUpdate(this.account);
  }

  delete(){
    this.currencyService.accountDelete(this.account);
  }

  ngOnInit(): void {
    this.account = Object.assign({},this.accountInput)
  }

}
