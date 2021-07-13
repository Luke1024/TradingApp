import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { AccountDto as AccountDto } from '../models/account';
import { AccountInfoDto } from '../models/account-info';
import { OrderDto } from '../models/order';
import { OrderInfoDto } from '../models/order-info';
import { State } from '../models/state';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  @Input() accountInput!:AccountDto

  account!:AccountDto

  correctness!:AccountInfoDto

  edit:boolean;

  //parameters in edit mode when created
  accountName!:string;
  //detection when lowered;
  leverage!:number;

  constructor(private currencyService:CurrencyService) {
    this.edit = true;
    this.correctness = {nameInfo:"", leverageInfo:"", balanceInfo:"", status:false} as AccountInfoDto
  }

  ngOnInit(): void {
    this.account = Object.assign({},this.accountInput)
    this.onChange()
    this.currencyService.pulseStream.subscribe(pulse => {
      if(pulse){
        this.getAccount(this.account);
      }
    })
  }

  private getAccount(account:AccountDto){
    if(account.created){
      this.currencyService.getAccount(account).subscribe(account => {
        if(account != null){
          if(account.status){
            this.update(account.accountDto);
          }
        }
      });
    }
  }

  addOrder(){
    var order:OrderDto = {
      accountId:this.account.id,
      id:1,
      currency:"EUR/USD",
      lot:0.1,
      tpPips:0,
      tpVal:0,
      slPips:0,
      slVal:0,
      profit:0,
      longOrder:true,
      created:false
    }
    this.account.orders.push(order)
  }

  settings(){
    this.edit = true;
  }

  cancelEditSettings(){
    this.edit = false;
  }

  saveSettings(){
    this.currencyService.accountUpdate(this.account).subscribe(response => {
      if(response != null){
        if(response.status = true){
          if(response.accountDto != null){
            this.update(response.accountDto);
            this.edit = false;
          }
        }
      }
    })
  }

  onChange(){
    this.currencyService.getAccountInfo(this.account).subscribe(response => {
      if(response != null){
          this.setInfoMessages(response);
      }
    })
  }

  private setInfoMessages(accountInfo:AccountInfoDto){
    this.correctness = accountInfo;
  }

  // creation state methods

  saveAccount(){
    if(this.correctness.status){
      this.currencyService.accountSave(this.account).subscribe(response => {
        if(response != null){
          if(response.status){
            if(response.accountDto != null){
              this.update(response.accountDto)
              this.edit = false;
            }
          }
        }
      })
    }
  }

  updateAccount(){
    if(this.correctness.status){
      this.currencyService.accountUpdate(this.account).subscribe(response => {
        if(response != null){
          if(response.status){
            if(response.accountDto != null){
              this.update(response.accountDto)
              this.edit = false;
            }
          }
        }
      })
    }
  }

  private update(accountDto:AccountDto){
    this.account = accountDto;
    if( ! this.edit){
      this.accountName = accountDto.accountName;
      this.leverage = accountDto.leverage;
    }
  }

  delete(){
    //to solve
  }
}
