import { OrderInfoDto } from "./order-info";
import { State } from "./state";

export interface OrderDto {
    accountId:number;
    id:number;
    lot:number;
    tpPips:number;
    tpVal:number;
    slPips:number;
    slVal:number;
    profit:number;
    lastRefreshed:string;
    created:boolean;
}