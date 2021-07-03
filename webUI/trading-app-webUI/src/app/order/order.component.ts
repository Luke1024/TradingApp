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

  state = State

  constructor(private currencyService:CurrencyService) { }

  editSlTp(){
    this.order.state = State.EDIT
    this.update()
  }

  saveSlTp(){
    this.order.state = State.OPEN
    this.update()
  }

  cancelEdit(){
    this.order.state = State.OPEN
    this.update()
  }

  openOrder(){
    this.order.state = State.OPEN
    this.update()
  }


  update(){
    this.currencyService.orderUpdate(this.order);
  }

  delete(){
    this.currencyService.deleteOrder(this.order);
  }

  ngOnInit(): void {
    this.order = Object.assign({},this.orderInput);
  }
}
