import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from "@angular/common/http"
import { DataPointDto } from "./data-point-dto"
import { Injectable } from "@angular/core"
import { Observable, Subject } from "rxjs"
import { Account } from "./models/account"
import { Order } from "./models/order"
import { TradingState } from "./models/trading-state"
import { catchError } from "rxjs/operators"

@Injectable({
    providedIn: 'root'
  })
export class CurrencyService {
    rootUrl = 'http://localhost:8080/trading/currencydata'

    tokenUrl = ""
    accountUrl = ""
    orderUrl = ""

    token=""

    private tradingData = new Subject()
    tradingDataStream = this.tradingData.asObservable()

    constructor(private http:HttpClient) {
    }

    ngOnInit(){
        this.getToken();
    }

    //token
    public getToken():void {
        this.http.get<string>(this.tokenUrl, {observe:'response'})
        .pipe(catchError(async (error) => this.handlingError(error)))
        .subscribe(response => this.setToken(response));
    }

    private setToken(response:any){
        if(response != null){
            var status = response.status
            if(response.body != null){
              if(status==200){
                  this.token = response.body
              }
            }
        }
    }


    //chart data
    public getData():Observable<DataPointDto[]> {
        return this.http.get<DataPointDto[]>(this.rootUrl)
    }

    //account
    public accountCreate(accountDto:Account){
        this.http.post<TradingState>(this.accountUrl + this.token, accountDto, {observe:'response'})
        .pipe(catchError(async (error) => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    public accountUpdate(accountDto:Account){
        this.http.put<TradingState>(this.accountUrl + this.token, accountDto, {observe:'response'})
        .pipe(catchError(async (error) => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    public accountDelete(accountDto:Account){
        this.http.delete<TradingState>(this.accountUrl + this.token + '/' + accountDto.id.toString())
        .pipe(catchError(async (error) => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    //order
    public orderCreate(orderDto:Order){
        this.http.post<TradingState>(this.accountUrl + this.token, orderDto, {observe:'response'})
        .pipe(catchError(async (error) => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    public orderUpdate(orderDto:Order){
        this.http.put<TradingState>(this.accountUrl + this.token, orderDto, {observe:'response'})
        .pipe(catchError(async (error) => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    public deleteOrder(orderDto:Order){
        this.http.delete<TradingState>(this.accountUrl + this.token + '/' + orderDto.id.toString())
        .pipe(catchError(async (error) => this.handlingError(error)))
        .subscribe(response => this.setResponse(response));
    }

    private setResponse(response:any){
        if(response != null){
            var status = response.status
            if(response.body != null){
                if(status==200){
                    this.token = response.body.token
                    this.tradingData.next(response.body)
                }
            }
        }
    }

    private handlingError(error:any){
        console.log(error)
    }
}