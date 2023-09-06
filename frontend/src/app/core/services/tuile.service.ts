import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {SERVER_API_URL} from "../../app.constants";
import {environment} from "../../../environments/environment";
import {HomeCard} from "../interfaces/infotuiles/homeCard";
import {BehaviorSubject, catchError, map, Observable, of, tap, throwError} from "rxjs";
import {Utilisateur} from "../interfaces/utilisateur";
import {Detail} from "../interfaces/infotuiles/detail";
import {Campagne} from "../interfaces/infotuiles/campagne";
import {NotificationService} from "./notification.service";
@Injectable({providedIn: 'root'})
export class TuileService {
  readonly url = environment.apiUrl


  private _infoTuiles$: BehaviorSubject<HomeCard[]> = new BehaviorSubject<HomeCard[]>(null);
  private _campagnesProduits$: BehaviorSubject<HomeCard> = new BehaviorSubject<HomeCard>(null);
  private _campagnesRegion$: BehaviorSubject<HomeCard> = new BehaviorSubject<HomeCard>(null);
  private _campagnesAnnnes$: BehaviorSubject<HomeCard> = new BehaviorSubject<HomeCard>(null);
  constructor(private http: HttpClient,private notification: NotificationService) {
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
          },
          catchError((err) => {
            //this.notification.error(" erreur de recuperation config ")
            return throwError(() => err)
          })
        )
      )
   /* const produit:HomeCard=HomeCard.fromJson({
      annee:new Date(),
      typeTuile: null,
      valeur:null,
      details: null,
      campagnes: this.dataTest,
      descriptif:"tes",
      value:123
    },HomeCard)*/

    //this.setCampagnesProduits(produit)

    //return of();
  }
  getcampagnesRegions(annee:number): Observable<HomeCard> {

    return this.http.get<HomeCard>(this.url + `/v1/reporting/chargementByRegion/${annee}`)
      .pipe(
        tap((res)=>{
            this.setCampagnesRegion(HomeCard.fromJson(res,HomeCard));
          },
          catchError((err) => {
            this.notification.error(" erreur de recuperation config ")
            return throwError(() => err)
          })
        )
      )
    /*const regions:HomeCard=HomeCard.fromJson({
      annee:new Date(),
      typeTuile: null,
      valeur:null,
      details: null,
      campagnes: this.dataRegion,
      descriptif:"production Regionnale",
      value:123
    },HomeCard)
    this.setCampagnesRegion(regions)
 console.error("xxxxxxxxxxxxxxxx")
    return of();
    */
  }

  getcampagnesAnnnes(annee:number): Observable<HomeCard> {
    return this.http.get<HomeCard>(this.url + `/v1/reporting/getChargementsAnnuel/${annee}`)
      .pipe(
        tap((res)=>{
            this.setCampagnesAnnnes(HomeCard.fromJson(res,HomeCard));
          },
          catchError((err) => {
            this.notification.error(" erreur de recuperation config ")
            return throwError(() => err)
          })
        )
      )
   /* const produit:HomeCard=HomeCard.fromJson({
      annee:new Date(),
      typeTuile: null,
      valeur:null,
      details: null,
      campagnes: this.dataAnnee,
      descriptif:"tes",
      value:123
    },HomeCard)
    this.setCampagnesAnnnes(produit)

    return of();
    */
  }
  dataTest = [
    { name: "ATTAPULGITE", value: 105000 },
    { name: "BASALTE", value: 55000 },
    { name: "ARGILE", value: 15000 },
    { name: "COQUILLAGE", value: 150000 },
    { name: "GRES", value: 20000 },
    { name: "LATERITE", value: 105000 },
    { name: "MANGANESE", value: 55000 },
    { name: "PHOSPHATE", value: 15000 },
    { name: "QUARTZITE", value: 150000 },
    { name: "SABLE", value: 20000 },
    { name: "SILEX", value: 105000 },
    { name: "ZIRCON", value: 55000 }
  ]
  dataRegion = [
    { name: "DAKAR", value: 105000 },
    { name: "TAMBACOUNDA", value: 55000 },
    { name: "THIES", value: 15000 },
    { name: "KOLDA", value: 150000 },
    { name: "ZIGUINCHOR", value: 20000 },
    { name: "KEDOUGOU", value: 105000 },
    { name: "SAINT-LOUIS", value: 55000 },
    { name: "LOUGA", value: 15000 },
    { name: "DIOURBEL", value: 150000 },
  ]
  dataAnnee = [
    { name: "2020", value: 105 },
    { name: "2021", value: 550 },
    { name: "2022", value: 150},
    { name: "2023", value: 150 },
    { name: "2019", value: 200}
  ]
}
