import { AccountDto } from "./account";

export interface TradingStateDto {
    token:string;
    accounts: AccountDto[];
}