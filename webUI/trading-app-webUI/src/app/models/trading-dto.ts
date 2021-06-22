import { AccountDto } from "./account-dto";

export interface TradingDto {
    token:string;
    accounts: AccountDto[];
}