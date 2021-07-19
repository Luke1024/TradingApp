import { OrderDto } from "./order";

export interface OrderResponseDto {
    status:boolean;
    orderDto:OrderDto;
}