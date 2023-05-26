import {Injectable} from "@angular/core";
import {interval, Observable, Subject, takeWhile} from "rxjs";

@Injectable({
  providedIn:'root'
})
export class SessionTimerService {
  private sessionTimeoutSeconds: number = 100; // 30 minutes
  private timerIntervalSeconds: number = 1; // 1 seconde

  private timer$: Observable<number>;
  private timerSubscription: any;
  private tempsRestant: number;

  private sessionExpireeSubject: Subject<void> = new Subject<void>();
  constructor() {
  }
  startTimer(){
    this.tempsRestant = this.sessionTimeoutSeconds;
    this.timer$ = interval(this.timerIntervalSeconds * 1000)
      .pipe(takeWhile(()=>this.tempsRestant>0))
    this.timerSubscription=this.timer$.subscribe(()=>{
      console.log(" temps rest  :"+this.tempsRestant)
      this.tempsRestant--;
      if (this.tempsRestant === 0) {
        this.sessionExpireeSubject.next();
      }
    })
  }
  resetTimer(){
    this.timerSubscription.unsubscribe();
    this.startTimer();
  }
  getTempsRestant() {
    return this.tempsRestant;
  }
  getSessionExpiree(){
    return this.sessionExpireeSubject.asObservable();
  }
  stopTimer() {
    if (this.timerSubscription) {
      this.timerSubscription.unsubscribe();
    }
  }
}
