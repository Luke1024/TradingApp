import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from "@angular/common/http"
import { DataPointDto } from "./data-point-dto"
import { Injectable } from "@angular/core"
import { Observable, Subject } from "rxjs"
import { AccountDto } from "./models/account"
import { OrderDto } from "./models/order"
import { TradingStateDto } from "./models/trading-state"
import { catchError } from "rxjs/operators"
import { StringDto } from "./models/string-dto"

@Injectable({
    providedIn: 'root'
  })
export class CurrencyService {
    rootUrl = 'http://localhost:8080/trading/currencydata'

    tokenUrl = "http://localhost:8080/trading/token/"
    accountUrl = "http://localhost:8080/trading/account/"
    orderUrl = "http://localhost:8080/trading/order/"

    token=""

    private tradingData = new Subject()
    tradingDataStream = this.tradingData.asObservable()

    constructor(private http:HttpClient) {
    }

    //token
    public getToken():void {
        this.http.get<StringDto>(this.tokenUrl, {observe:'response'})
        .pipe(catchError(error => this.handlingError(error)))
        .subscribe(token => this.setToken(token))
    }

    private setToken(response:any){
        if(response != null){
            var status = response.status
            if(response.body != null){
              if(status==200){
                if(response.body?.message != null){
                    console.log("Setting token " + response.body.message)
                    this.token = response.body.message
                }
              }
            }
          }
        }


    //chart data
    public getData():Observable<DataPointDto[]> {
        return this.http.get<DataPointDto[]>(this.rootUrl)
    }

    //account
    public accountCreate(accountDto:AccountDto){
        this.http.post<AccountDto[]>(this.accountUrl + this.token, accountDto, {observe:'response'})
        .pipe(catchError(error => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    public accountUpdate(accountDto:AccountDto){
        this.http.put<AccountDto[]>(this.accountUrl + this.token, accountDto, {observe:'response'})
        .pipe(catchError(error => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    public accountDelete(accountDto:AccountDto){
        this.http.delete<AccountDto[]>(this.accountUrl + this.token + '/' + accountDto.id.toString())
        .pipe(catchError(error => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    //order
    public orderCreate(orderDto:OrderDto, account:AccountDto){
        this.http.post<AccountDto[]>(this.orderUrl + this.token + '/' + account.id,orderDto, {observe:'response'})
        .pipe(catchError(error => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    public orderUpdate(orderDto:OrderDto){
        this.http.put<AccountDto[]>(this.orderUrl + this.token, orderDto, {observe:'response'})
        .pipe(catchError(error => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    public deleteOrder(orderDto:OrderDto){
        this.http.delete<AccountDto[]>(this.orderUrl + this.token + '/' + orderDto.id.toString())
        .pipe(catchError(error => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    private setResponse(response:any){
        if(response != null){
            var status = response.status
            if(response.body != null){
                if(status==200){
                    this.tradingData.next(response.body)
                }
            }
        }
    }


    handlingError(error: HttpErrorResponse) {
        return new Observable(observer => {
          if (error.error instanceof ErrorEvent) {
            //general error
          } else {
            //backend error
            var errorValue:string = error.error.value
            var errorStatus:number = error.status
          }
          observer.next(null)
      })
    }
}