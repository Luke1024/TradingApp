import { trigger } from '@angular/animations';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { AccountDto as AccountDto } from '../models/account';
import { AccountInfoDto } from '../models/account-info';
import { OrderDto } from '../models/order-dto';
import { OrderInfoDto } from '../models/order-info';
import { State } from '../models/state';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  
  @Output() autoRemove = new EventEmitter<AccountDto>()

  @Input() account!:AccountDto

  orders:OrderDto[] = [] 

  closedOrders:OrderDto[] = []

  correctness:AccountInfoDto

  edit:boolean = true;

  constructor(private currencyService:CurrencyService) {
    this.correctness = {nameInfo:"", leverageInfo:"", balanceInfo:"", status:false} as AccountInfoDto
  }

  ngOnInit(): void {
    if(this.account.created){
      this.edit = false;
      this.getOrders()
      this.getClosedOrders()
    } else {
      this.onChange()
    }
    this.currencyService.pulseStream.subscribe(pulse => {
      if(pulse){
        this.getAccount(this.account);
      }
    })
  }

  private getOrders(){
    this.currencyService.getAllOrders(this.account).subscribe(orders => {
      if(orders != null){
        this.orders = orders;
      }
    })
  }

  private getClosedOrders(){
    this.currencyService.getAllClosedOrders(this.account).subscribe(closedOrders => {
      if(closedOrders != null){
        this.closedOrders = closedOrders;
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
      id:0,
      currency:"EUR/USD",
      lot:0.1,
      tpPips:0,
      tpVal:0,
      slPips:0,
      slVal:0,
      profit:0,
      longOrder:true,
      isOpenedStatus:false,
      isClosedStatus:false,
      createdStatus:false
    }
    this.orders.push(order)
  }

  deleteOrder(order:OrderDto){
    this.orders.forEach((orderFromList, index)=>{
      if(order==orderFromList){
        this.orders.splice(index,1)
      }
    })
  }

  settings(){
    this.edit = true;
  }

  cancelEditSettings(){
    this.edit = false;
  }

  saveSettings(){
    if(this.correctness.status){
    this.currencyService.accountUpdate(this.account).subscribe(response => {
      if(response != null){
        if(response.status){
          if(response.accountDto != null){
            this.update(response.accountDto);
            this.edit = false;
          }
        }
      }
    })
  }
}

  onChange(){
    this.currencyService.getAccountInfo(this.account).subscribe(response => {
      if(response != null){
          this.setInfoMessages(response);
      }
    })
  }

  private setInfoMessages(accountInfo:AccountInfoDto){
    //map to not restart models 
    this.correctness.balanceInfo = accountInfo.balanceInfo
    this.correctness.leverageInfo = accountInfo.leverageInfo
    this.correctness.nameInfo = accountInfo.nameInfo
    this.correctness.status = accountInfo.status
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

  private update(accountDto:AccountDto){
    this.smartMapping(accountDto);
  }

  private smartMapping(account:AccountDto){
    this.account.id = account.id
    if(!this.edit){
      this.account.accountName = account.accountName
      this.account.leverage = account.leverage
    }
    this.account.balance = account.balance
    this.account.created = account.created
  }

  delete(){
    if(this.account.created){
      this.deleteFromBackEndAndFrontEnd();
    } else {
      this.deleteFromFrontEnd();
    }
  }

  private deleteFromBackEndAndFrontEnd(){
    this.currencyService.accountDelete(this.account).subscribe(response => {
      if(response){
        this.deleteFromFrontEnd()
      }
    })
  }

  private deleteFromFrontEnd(){
    this.autoRemove.emit(this.account)
  }
}
