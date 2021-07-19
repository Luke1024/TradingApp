import { Component, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { AccountDto } from '../models/account';

@Component({
  selector: 'app-trading-view',
  templateUrl: './trading-view.component.html',
  styleUrls: ['./trading-view.component.css']
})
export class TradingViewComponent implements OnInit {

  constructor(private currencyService:CurrencyService) { }

  ngOnInit(): void {
    this.currencyService.getToken()
  }

  accounts:AccountDto[] = []
  

  addAccount() {
    var account:AccountDto = {
      id:1,
      accountName:"",
      leverage:10,
      balance:10000,
      created:false,
      orders:[]
    }
    this.accounts.push(account)
  }
}
