import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RefreshService {

  constructor() { }

  private updatePulse = new Subject()
  updatePulseStream = this.updatePulse.asObservable()

  updateRefreshTime(lastRefreshed:string){

  }

  executePulse(){
    this.updatePulse.next(true);
  }
}
