import { Component, Input, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { Order } from '../models/order';
import { State } from '../models/state';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  @Input() orderInput!:Order

  order!:Order

  state = State

  constructor(private currencyService:CurrencyService) { }


  create(){
    this.currencyService.orderCreate(this.order);
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
