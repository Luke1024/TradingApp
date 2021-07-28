import { MinLengthValidator } from "@angular/forms";
import { OrderState } from "./order-state";

export interface OrderDto {
    accountId:number;
    id:number;
    currency:string;
    lot:number;
    tpPips:number;
    tpVal:number;
    slPips:number;
    slVal:number;
    profit:number;
    longOrder:boolean;
    orderState:OrderState;
}