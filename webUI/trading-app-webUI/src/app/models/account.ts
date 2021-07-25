
import { OrderDto } from "./order-dto";
import { State } from "./state";

export interface AccountDto {
    id:number;
    accountName:string;
    leverage:number;
    balance:number;
    created:boolean;
    orders:OrderDto[];
}