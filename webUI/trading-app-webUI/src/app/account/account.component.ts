import { Component, Input, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { AccountDto as AccountDto } from '../models/account';
import { AccountInfoDto } from '../models/account-info';
import { OrderDto } from '../models/order';
import { OrderInfoDto } from '../models/order-info';
import { State } from '../models/state';
import { RefreshService } from '../refresh-service.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  @Input() accountInput!:AccountDto

  account!:AccountDto

  //correctnessMessages;
  nameInfo!:string;
  leverageInfo!:string;
  balanceInfo!:string;

  edit:boolean
  message:string
  correctnessStatus:boolean;

  //parameters in edit mode when created
  accountName!:string;
  //detection when lowered;
  leverage!:number;

  constructor(private currencyService:CurrencyService, private refreshService:RefreshService) {
    this.edit = true;
    this.message = "";
    this.correctnessStatus = false;
  }

  ngOnInit(): void {
    this.account = Object.assign({},this.accountInput)
    this.refreshService.updatePulseStream.subscribe(pulse => {
      if(pulse){
        this.update(this.account)
      }
    })
  }

  addOrder(){
    var order:OrderDto = {
      accountId:this.account.id,
      id:1,
      lot:0.1,
      tpPips:0,
      tpVal:0,
      slPips:0,
      slVal:0,
      profit:0,
      lastRefreshed: "",
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

  refreshState(){
    this.currencyService.getAccount(this.account).subscribe(response => {
      if(response != null){
        this.update(response)
      }
    })
  }

  getCorrectnessInfo(){
    this.currencyService.getAccountInfo(this.account).subscribe(response => {
      if(response != null){
        this.correctnessStatus = response.status;
        if(!this.correctnessStatus){
          this.setInfoMessages(response);
        }
      }
    })
  }

  private setInfoMessages(accountInfo:AccountInfoDto){
    this.nameInfo=accountInfo.nameInfo;
    this.leverageInfo=accountInfo.leverageInfo;
    this.balanceInfo=accountInfo.balanceInfo;
  }

  // creation state methods

  saveAccount(){
    if(this.correctnessStatus){
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
    if(this.correctnessStatus){
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
