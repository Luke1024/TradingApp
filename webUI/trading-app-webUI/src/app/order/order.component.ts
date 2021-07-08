import { Component, Input, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { OrderDto } from '../models/order';
import { State } from '../models/state';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  @Input() orderInput!:OrderDto;

  order!:OrderDto;

  //correctnessMessages;


  edit:boolean;
  message:string;
  correctnessStatus:boolean;

  //parameters in edit mode when created
  tpPips!:number;
  slPips!:number;

  constructor(private currencyService:CurrencyService) { 
    this.edit = true;
    this.message = "";
    this.correctnessStatus = false;
  }

  getCorrectnessInfo(){
    this.currencyService.getOrderInfo(this.order).subscribe(response => {
      if(response != null){
        this.correctnessStatus = response.status;
        this.order.tpVal = response.tpVal;
        this.order.slVal = response.slVal;
        if(!this.correctnessStatus){

        }
      }
    })
  }

  editSlTp(){
    this.edit = true;
  }

  saveSlTp(){
    if(this.correctnessStatus && this.order.created){
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
    if( ! this.order.created && this.correctnessStatus){
      this.currencyService.saveOrder(this.order).subscribe(response => {
        if(response.status){
          this.update(response.orderDto)
          this.edit = false;
        }
      })
    }
  }

  private update(orderDto:OrderDto){
    this.order = orderDto
    if(!this.edit){
      this.tpPips = orderDto.tpPips;
      this.slPips = orderDto.slPips;
    }
  }

  delete(){
    //to solve
  }

  ngOnInit(): void {
    this.order = Object.assign({},this.orderInput);
  }
}
