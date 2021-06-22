import { OrderDto } from "./order-dto";

export interface AccountDto {
    id:number;
    accountName:string;
    leverage:number;
    balance:number;
    edit:boolean;
    orders:OrderDto[];
}