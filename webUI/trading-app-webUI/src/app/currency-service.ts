import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from "@angular/common/http"
import { DataPointDto } from "./data-point-dto"
import { Injectable } from "@angular/core"
import { Observable } from "rxjs"

@Injectable({
    providedIn: 'root'
  })
export class CurrencyService {
    rootUrl = 'http://localhost:8080/trading/currencydata'

    constructor(private http:HttpClient) {}

    public getData():Observable<DataPointDto[]> {
        return this.http.get<DataPointDto[]>(this.rootUrl)
    }
}