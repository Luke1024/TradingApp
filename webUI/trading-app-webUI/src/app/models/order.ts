import { OrderInfoDto } from "./order-info";
import { State } from "./state";

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
    created:boolean;
}