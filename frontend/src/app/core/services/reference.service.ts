import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {HomeCard} from "../interfaces/infotuiles/homeCard";
import {BehaviorSubject, catchError, map, Observable, tap, throwError} from "rxjs";
import {Produit} from "../interfaces/produit";
import {NotificationService} from "./notification.service";
import {Site} from "../interfaces/site";

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
            this.setAnneeMax(res[0])
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
