import { Account } from "./account";

export interface TradingState {
    token:string;
    accounts: Account[];
}