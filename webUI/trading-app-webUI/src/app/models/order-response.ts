import { OrderDto } from "./order-dto";

export interface OrderResponseDto {
    status:boolean;
    orderDto:OrderDto;
}