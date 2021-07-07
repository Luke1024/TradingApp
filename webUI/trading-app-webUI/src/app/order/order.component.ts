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

  @Input() orderInput!:OrderDto

  order!:OrderDto

  edit:boolean
  message:string
  correctnessStatus:boolean;

  constructor(private currencyService:CurrencyService) { 
    this.edit = false;
    this.message = "";
    this.correctnessStatus = false;
  }

  getCorrectnessInfo(){
    this.currencyService.getOrderInfo(this.order).subscribe(response => {
      if(response != null){
        this.correctnessStatus = response.status;
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
          this.order = response.orderDto
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
          this.order = response.orderDto
        }
      })
    }
  }

  delete(){
    //to solve
  }

  ngOnInit(): void {
    this.order = Object.assign({},this.orderInput);
  }
}
