import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, Observable, tap, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { HomeCard } from "../interfaces/infotuiles/homeCard";
import { NotificationService } from "./notification.service";
import {Globals} from "../../app.constants";
@Injectable({providedIn: 'root'})
export class TuileService {
  readonly url = environment.apiUrl


  private _infoTuiles$: BehaviorSubject<HomeCard[]> = new BehaviorSubject<HomeCard[]>(null);
  private _campagnesProduits$: BehaviorSubject<HomeCard> = new BehaviorSubject<HomeCard>(null);
  private _campagnesRegion$: BehaviorSubject<HomeCard> = new BehaviorSubject<HomeCard>(null);
  private _campagnesAnnnes$: BehaviorSubject<HomeCard> = new BehaviorSubject<HomeCard>(null);
  constructor(private http: HttpClient,private notification: NotificationService,
              private globals: Globals) {
  }

  /**
   * Méthode permettant d'aller interroger l'API pour récupérer les infos-tuiles associé à l'utilisateur connecté.
   */
  getInfosTuiles(): Observable<HomeCard[]> {
    return this.http.get<HomeCard[]>(this.url + '/chargementInfoTuiles')
      .pipe(
        tap((res: HomeCard[]) => {
          res.map(a=>HomeCard.fromJson(res, HomeCard))
          this.setInfoTuiles(res)
        }),
        catchError((err) => {
          return throwError(() => err) // RXJS 7+
        })
      )
  }

  get infoTuiles$(): Observable<HomeCard[]> { // getter ou selector
    return this._infoTuiles$.asObservable()
  }

  setInfoTuiles(res: HomeCard[]) {
    this._infoTuiles$.next(res)
  }
  get campagnesRegions$(): Observable<HomeCard> {
    return this._campagnesRegion$.asObservable()
  }

  setCampagnesRegion(res: HomeCard) {
    this._campagnesRegion$.next(res)
  }
  get campagnesProduits$(): Observable<HomeCard> {
    return this._campagnesProduits$.asObservable()
  }

  setCampagnesProduits(res: HomeCard) {
    this._campagnesProduits$.next(res)
  }

  get campagnesAnnnes$(): Observable<HomeCard> {
    return this._campagnesAnnnes$.asObservable()
  }

  setCampagnesAnnnes(res: HomeCard) {
    this._campagnesAnnnes$.next(res)
  }

  getcampagnesProduits(annee:number): Observable<HomeCard> {
    console.error("annnee",annee)
    return this.http.get<HomeCard>(this.url + `/v1/reporting/produitParAn/${annee}`)
      .pipe(
        tap((res)=>{
            this.setCampagnesProduits(HomeCard.fromJson(res,HomeCard));
            this.globals.loading = false;
          },
          catchError((err) => {
            //this.notification.error(" erreur de recuperation config ")
            this.globals.loading = false;
            return throwError(() => err)
          })
        )
      )

  }
  getcampagnesRegions(annee:number): Observable<HomeCard> {
    this.globals.loading = true;
    return this.http.get<HomeCard>(this.url + `/v1/reporting/chargementByRegion/${annee}`)
      .pipe(
        tap((res)=>{
            this.setCampagnesRegion(HomeCard.fromJson(res,HomeCard));
            this.globals.loading = false;
          },
          catchError((err) => {
            this.notification.error(" erreur de recuperation config ")
            this.globals.loading = false;
            return throwError(() => err)
          })
        )
      )
  }

  getcampagnesAnnnes(annee:number): Observable<HomeCard> {
    return this.http.get<HomeCard>(this.url + `/v1/reporting/recouvrementAnnuel`)
      .pipe(
        tap((res) => {
            this.setCampagnesAnnnes(HomeCard.fromJson(res, HomeCard));
          },
          catchError((err) => {
            this.notification.error(" erreur de recuperation config ")
            return throwError(() => err)
          })
        )
      )
  }

}
