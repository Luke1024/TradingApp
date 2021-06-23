import { State } from "./state";

export interface Order {
    id:number;
    currency:string;
    lot:number;
    tpPips:number;
    tpVal:number;
    slPips:number;
    slVal:number;
    profit:number;
    state:State
    message:string;
}