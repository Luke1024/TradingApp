import { AccountDto } from "./account";

export interface AccountResponseDto {
    status:boolean;
    accountDto:AccountDto;
}