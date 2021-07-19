import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from "@angular/common/http"
import { DataPointDto } from "./data-point-dto"
import { Injectable, OnInit } from "@angular/core"
import { Observable, of, Subject } from "rxjs"
import { AccountDto } from "./models/account"
import { OrderDto } from "./models/order"
import { catchError } from "rxjs/operators"
import { StringDto } from "./models/string-dto"
import { AccountInfoDto } from "./models/account-info"
import { OrderInfoDto } from "./models/order-info"
import { AccountResponseDto } from "./models/account-response"
import { OrderResponseDto } from "./models/order-response"

@Injectable({
    providedIn: 'root'
  })
export class CurrencyService {
    rootUrl = 'http://localhost:8080/trading/'
    timeSeriesUrl =  this.rootUrl + 'currencydata/'

    tokenUrl = this.rootUrl + "token/"

    //account service urls
    accountTradingStateUrl = this.rootUrl + "account/all/"
    accountInfoUrl = this.rootUrl + "account/info/"
    accountUrl = this.rootUrl + "account/"
    accountSaveUrl = this.rootUrl + "account/save/"
    accountUpdateUrl = this.rootUrl + "account/update/"
    
    //order service urls
    orderInfoUrl = this.rootUrl + "order/info/"
    orderUrl = this.rootUrl + "order/"
    saveOrderUrl = this.rootUrl + "order/save/"
    updateOrderUrl = this.rootUrl + "order/update/"

    token=""

    private tradingData = new Subject()
    tradingDataStream = this.tradingData.asObservable()
    private pulse = new Subject()
    pulseStream = this.pulse.asObservable()

    constructor(private http:HttpClient) {
        setInterval(()=> {
            this.pulse.next(true);
        }, 5000)
    }

    //token
    public getToken():void {
        this.http.get<StringDto>(this.tokenUrl, {observe:'response'})
        .pipe(catchError(this.handleError<StringDto>("get token")))
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
        return this.http.get<DataPointDto[]>(this.timeSeriesUrl)
    }

    //account

    public getTradingState(): Observable<AccountDto[]> {
        return this.http.get<AccountDto[]>(this.accountTradingStateUrl + this.token)
//.pipe(catchError(this.handleError<AccountDto[]>(`load trading state`)))
    }

    public getAccountInfo(accountDto:AccountDto): Observable<AccountInfoDto> {
        return this.http.put<AccountInfoDto>(this.accountInfoUrl, this.accountDtoReducing(accountDto))
        .pipe(catchError(this.handleError<AccountInfoDto>('get account info')))
    }

    public getAccount(accountDto:AccountDto): Observable<AccountResponseDto> {
        return this.http.get<AccountResponseDto>(this.accountUrl + '/' + this.token + '/' + accountDto.id)
        .pipe(catchError(this.handleError<AccountResponseDto>('get account')))
    }

    public accountSave(accountDto:AccountDto): Observable<AccountResponseDto>{
        return this.http.put<AccountResponseDto>(this.accountSaveUrl + '/' + this.token, this.accountDtoReducing(accountDto))
        .pipe(catchError(this.handleError<AccountResponseDto>('account save')))
    }

    public accountUpdate(accountDto:AccountDto):Observable<AccountResponseDto> {
        return this.http.put<AccountResponseDto>(this.accountUpdateUrl + this.token, this.accountDtoReducing(accountDto))
        .pipe(catchError(this.handleError<AccountResponseDto>('account update')))
    }

    public accountDelete(accountDto:AccountDto): Observable<boolean>{
        return this.http.delete<boolean>(this.accountUrl + '/' + this.token + '/' + accountDto.id)
        .pipe(catchError(this.handleError<boolean>('account delete')))
    }

    private accountDtoReducing(accountDto:AccountDto):AccountDto{
        accountDto.orders = []
        return accountDto; 
    }

    //order

    public getOrderInfo(orderDto:OrderDto): Observable<OrderInfoDto>{
        return this.http.put<OrderInfoDto>(this.orderInfoUrl, orderDto)
        .pipe(catchError(this.handleError<OrderInfoDto>("order info")))
    }

    public getOrder(orderDto:OrderDto): Observable<OrderResponseDto>{
        return this.http.get<OrderResponseDto>(this.orderUrl + this.token + '/' + orderDto.id)
        .pipe(catchError(this.handleError<OrderResponseDto>("get order")))
    }

    public saveOrder(orderDto:OrderDto): Observable<OrderResponseDto>{
        return this.http.put<OrderResponseDto>(this.saveOrderUrl + this.token, orderDto)
        .pipe(catchError(this.handleError<OrderResponseDto>("save order")))
    }

    public orderUpdate(orderDto:OrderDto): Observable<OrderResponseDto>{
        return this.http.put<OrderResponseDto>(this.updateOrderUrl + this.token, orderDto)
        .pipe(catchError(this.handleError<OrderResponseDto>("order update")))
    }

    public deleteOrder(orderDto:OrderDto): Observable<boolean>{
        return this.http.delete<boolean>(this.orderUrl + this.token + '/' + orderDto.id.toString())
        .pipe(catchError(this.handleError<boolean>("order delete")))
    }


    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
    
          // TODO: send the error to remote logging infrastructure
          console.error(error); // log to console instead
    
          // TODO: better job of transforming error for user consumption
          this.log(`${operation} failed: ${error.message}`);
    
          // Let the app keep running by returning an empty result.
          return of(result as T);
        };
      }
    
      /** Log a HeroService message with the MessageService */
      private log(message: string) {

      }
}