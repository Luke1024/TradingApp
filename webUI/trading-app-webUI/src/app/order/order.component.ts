import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { CurrencyService } from '../currency-service';
import { OrderDto } from '../models/order-dto';
import { OrderInfoDto } from '../models/order-info';
import { State } from '../models/state';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  @Output() autoRemove = new EventEmitter<OrderDto>()

  @Input() order!:OrderDto;

  correctness!:OrderInfoDto;

  edit:boolean;

  //parameters in edit mode when created
  tpPips!:number;
  slPips!:number;
  constructor(private currencyService:CurrencyService) {
    this.edit = true;
    this.correctness = {lotInfo:"", tpPipsInfo:"", tpVal:0, slPipsInfo:"", slVal:0, status:false} as OrderInfoDto;
  }

  onChange(){
    this.currencyService.getOrderInfo(this.order).subscribe(response => {
      if(response != null){
        this.correctness = response;
      }
    })
  }

  editSlTp(){
    this.edit = true;
  }

  saveSlTp(){
    if(this.correctness.status && ! this.order.created){
      this.currencyService.orderUpdate(this.order).subscribe(response => {
        if(response.status){
          this.update(response.orderDto)
          this.edit = false;
        } 
      })
    }
  }

  cancelEdit(){
    this.edit = false;
  }

  openOrder(){
    if(this.correctness.status){
      this.currencyService.saveOrder(this.order).subscribe(response => {
        if(response.status){
          this.update(response.orderDto)
          this.edit = false;
        }
      })
    }
  }

  private update(orderDto:OrderDto){
    this.order.accountId = orderDto.accountId
    this.order.id = orderDto.id
    this.order.currency = orderDto.currency
    this.order.lot = orderDto.lot
    this.order.tpVal = orderDto.tpVal
    this.order.slVal = orderDto.slVal
    this.order.profit = orderDto.profit
    this.order.longOrder = orderDto.longOrder
    this.order.created = orderDto.created
    if(!this.edit){
      this.tpPips = orderDto.tpPips;
      this.slPips = orderDto.slPips;
    }
  }

  delete(){
    if(this.order.created){
      this.deleteFromBackEndAndFrontEnd(); 
    } else {
      this.deleteFromFrontEnd();
    }
  }

  private deleteFromBackEndAndFrontEnd():void {
    this.currencyService.deleteOrder(this.order).subscribe(response => {
      if(response){
        this.deleteFromFrontEnd()
      }
    });
  }

  private deleteFromFrontEnd(){
    this.autoRemove.emit(this.order)
  }

  ngOnInit(): void {
    this.onChange()
    this.currencyService.pulseStream.subscribe(
      pulse => {
        if(pulse){
          this.getOrder(this.order)
        }
      }
    )
  }

  private getOrder(order:OrderDto){
    if(order.created){
      this.currencyService.getOrder(order).subscribe(response => {
        if(response != null){
          if(response.status){
            this.update(response.orderDto)
          }
        }
      })
    }
  }
}
