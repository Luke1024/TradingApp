import { Component, Input, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { AccountDto as AccountDto } from '../models/account';
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

  edit:boolean
  message:string
  correctnessStatus:boolean;



  //parameters in edit mode when created
  accountName!:string;
  //detection when lowered;
  leverage!:number;

  constructor(private currencyService:CurrencyService, private refreshService:RefreshService) {
    this.edit = false;
    this.message = "";
    this.correctnessStatus = false;
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
//open state methods
  settings(){
    this.edit = true;
  }

//edit state methods

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
      }
    })
  }

  saveSettings(){
    this.currencyService.accountUpdate(this.account).subscribe(response => {
      if(response != null){
        if(response.status = true){
          if(response.accountDto != null){
            this.update(response.accountDto);
          }
        }
      }
    })
  }

  cancelEditSettings(){
    this.edit = false;
  }

  // creation state methods

  saveAccount(){
    if(this.correctnessStatus){
      this.currencyService.accountSave(this.account).subscribe(response => {
        if(response != null){
          if(response.status){
            if(response.accountDto != null){
              this.update(response.accountDto)
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
            }
          }
        }
      })
    }
  }

  update(accountDto:AccountDto){
    this.account = accountDto;
    this.sendLastRefreshed(accountDto.lastRefreshed)
    if( ! this.edit){
      this.accountName = accountDto.accountName;
      this.leverage = accountDto.leverage;
    }
  }

  private sendLastRefreshed(lastRefreshed:string){
    this.refreshService.updateRefreshTime(lastRefreshed)
  }

  delete(){
    //to solve
  }

  ngOnInit(): void {
    this.account = Object.assign({},this.accountInput)
    this.refreshService.updatePulseStream.subscribe(pulse => {
      if(pulse){
        this.update(this.account)
      }
    })
  }
}
