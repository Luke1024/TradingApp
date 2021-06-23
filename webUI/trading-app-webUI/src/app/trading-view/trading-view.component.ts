import { Component, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { State } from '../models/state';
import { TradingState } from '../models/trading-state';

@Component({
  selector: 'app-trading-view',
  templateUrl: './trading-view.component.html',
  styleUrls: ['./trading-view.component.css']
})
export class TradingViewComponent implements OnInit {

  constructor(private currencyService:CurrencyService) { }

  ngOnInit(): void {
    this.currencyService.tradingDataStream.subscribe(data => this.tradingDto = data as TradingState)
  }
  tradingDto2:TradingState = {
    token: "",
    accounts: []
  }

  tradingDto:TradingState = {
    token: "sdasdasd",
    accounts: [{
      id: 42,
      accountName: "Test Account",
      leverage: 100,
      balance: 23400,
      state:State.OPEN,
      message:"",
      orders: [{
        id: 23,
        currency: "EUR/USD",
        lot: 0.5,
        tpPips: 100,
        tpVal: 2500,
        slPips: 30,
        slVal: 1000,
        profit: 300,
        state:State.OPEN,
        message:""
      },{
        id: 23,
        currency: "EUR/USD",
        lot: 0.5,
        tpPips: 100,
        tpVal: 2500,
        slPips: 30,
        slVal: 1000,
        profit: 300,
        state:State.CREATION,
        message:""
      }
    ]
    },
    {
      id: 42,
      accountName: "Test Account",
      leverage: 100,
      balance: 23400,
      state:State.EDIT,
      message:"",
      orders: [{
        id: 23,
        currency: "EUR/USD",
        lot: 0.5,
        tpPips: 100,
        tpVal: 2500,
        slPips: 30,
        slVal: 1000,
        profit: 300,
        state: State.EDIT,
        message:""
      }]
  },
  {
    id: 42,
    accountName: "Test Account",
    leverage: 100,
    balance: 23400,
    state:State.CREATION,
    message:"",
    orders: []
  },
  {
    id: 42,
    accountName: "Test Account",
    leverage: 100,
    balance: 23400,
    state:State.CREATION,
    message:"",
    orders: []
  }

]

}
}
