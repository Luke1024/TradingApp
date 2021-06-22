import { Component, OnInit } from '@angular/core';
import { State } from '../models/state';
import { TradingDto } from '../models/trading-dto';

@Component({
  selector: 'app-trading-view',
  templateUrl: './trading-view.component.html',
  styleUrls: ['./trading-view.component.css']
})
export class TradingViewComponent implements OnInit {

  constructor() { }
  tpPips:number = 0;
  slPips:number = 0;
  accountName = ""
  leverageMax:number = 0

  ngOnInit(): void {
  }

  state = State

  tradingDto:TradingDto = {
    token: "sdasdasd",
    accounts: [{
      id: 42,
      accountName: "Test Account",
      leverage: 100,
      balance: 23400,
      state:State.OPEN,
      orders: [{
        id: 23,
        currency: "EUR/USD",
        lot: 0.5,
        tpPips: 100,
        tpVal: 2500,
        slPips: 30,
        slVal: 1000,
        profit: 300,
        state:State.OPEN
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
      }
    ]
    },
    {
      id: 42,
      accountName: "Test Account",
      leverage: 100,
      balance: 23400,
      state:State.EDIT,
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
      }]
  },
  {
    id: 42,
    accountName: "Test Account",
    leverage: 100,
    balance: 23400,
    state:State.CREATION,
    orders: []
  }

]

}
}
