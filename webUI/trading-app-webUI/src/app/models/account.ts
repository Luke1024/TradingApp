import { Order } from "./order";
import { State } from "./state";

export interface Account {
    id:number;
    accountName:string;
    leverage:number;
    balance:number;
    state: State;
    message: string;
    orders:Order[];
}