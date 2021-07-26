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

  tokenInMemory:boolean = false;

  ngOnInit(): void {
    if(this.tokenInMemory){
      this.currencyService.getToken()
    } else {
      this.currencyService.getAccountAll("hypotethical token").subscribe(accounts => {
        if(accounts != null){
          this.accounts = accounts;
        }
      })
    }
  }

  accounts:AccountDto[] = []
  
  deleteAccount(account:AccountDto){
    this.accounts.forEach((accountFromList, index)=>{
      if(account==accountFromList){
        this.accounts.splice(index,1);
      }
    })
  }

  addAccount() {
    var account:AccountDto = {
      id:1,
      accountName:"",
      leverage:10,
      balance:10000,
      created:false,
    }
    this.accounts.push(account)
  }
}
