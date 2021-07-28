import { MinLengthValidator } from "@angular/forms";

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
    isOpenedStatus:boolean;
    isClosedStatus:boolean;
    createdStatus:boolean;
}