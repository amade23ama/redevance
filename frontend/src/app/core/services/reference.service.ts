import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, Observable, tap, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { NotificationService } from "./notification.service";

@Injectable({
  providedIn: 'root'
})
export class ReferenceService{
  private url = environment.apiUrl ;
  private _annees$: BehaviorSubject<number[]> = new BehaviorSubject<number[]>( []);
  private annneMax:number
  constructor(private readonly httpClient:HttpClient,private notification: NotificationService) {

  }
  getAllAnnee(): Observable<number[]> {
    return this.httpClient.get<number[]>(this.url + `/v1/reporting/getAnnees`)
      .pipe(
        tap((res)=>{
            this.setAnneeMax(res.length>0 ? Math.max(...res) : new Date().getFullYear())
            this.setAnnees(res)
          },
          catchError((err) => {
            this.notification.error(" erreur de recuperation config ")
            return throwError(() => err)
          })
        )
      )
  }
  get annees$(): Observable<number[]> { // getter ou selector
    return this._annees$.asObservable()
  }

  setAnnees(res: number[]) {
    this._annees$.next(res)
  }
  setAnneeMax(annee:number) {
    this.annneMax = annee
  }
  getAnneeMax() {
    return this.annneMax;
  }
}
