import { Component, Input, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { AccountDto as AccountDto } from '../models/account';
import { OrderDto } from '../models/order';
import { State } from '../models/state';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  @Input() accountInput!:AccountDto

  state = State

  account!:AccountDto

  constructor(private currencyService:CurrencyService) {

  }

  addOrder(){
    var order:OrderDto = {
      id:1,
      currency:"EUR/USD",
      lot:0.1,
      tpPips:0,
      tpVal:0,
      slPips:0,
      slVal:0,
      profit:0,
      state:State.CREATION,
      message:""
    }
    this.currencyService.orderCreate(order,this.account)
  }
//open state methods
  settings(){
    this.account.state = State.EDIT
    this.update()
  }

//edit state methods

  saveSettings(){
    this.account.state = State.OPEN
    this.update()
  }

  cancelEditSettings(){
    this.account.state = State.OPEN
    this.currencyService.accountUpdate(this.account);
  }

  // creation state methods

  saveAccount(){
    this.account.state = State.OPEN
    this.update;
  }

  update(){
    this.currencyService.accountUpdate(this.account)
  }

  delete(){
    this.currencyService.accountDelete(this.account);
  }

  ngOnInit(): void {
    this.account = Object.assign({},this.accountInput)
  }
}
