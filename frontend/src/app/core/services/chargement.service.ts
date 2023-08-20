import {Injectable, NgModule} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Site} from "../interfaces/site";
import {BehaviorSubject, catchError, tap, throwError} from "rxjs";
import {Utilisateur} from "../interfaces/utilisateur";
import {Chargement} from "../interfaces/chargement";
import {NotificationService} from "./notification.service";

@Injectable({
  providedIn:"root"
})
export class ChargementService{
  /** url de base des webservices chargement */
  private url = environment.apiUrl + '/v1/chargement';
  private _chargements$: BehaviorSubject<Chargement[]> = new BehaviorSubject<Chargement[]>( []);

  chargementCourant: Chargement = new Chargement ();
  chargementOriginal: Chargement  = new Chargement ();
  constructor(private http:HttpClient,private notification: NotificationService) {
  }

  /**
   * recuperer ts les chargements
   */
  getAllChargements(){
    return this.http.get<Chargement[]>(this.url+"/rechercher")
      .pipe(
        tap((res:Chargement[]) => {
          this.setChargements(res);
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Utilisateur ")
          return throwError(() => err)
        })
      )
  }
  setChargements(chargements:Chargement[]){
    this._chargements$.next(chargements)
  }
  get chargements$(){
    return this._chargements$.asObservable();
  }
}
