import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { saveAs } from "file-saver";
import { BehaviorSubject, Observable, catchError, share, tap, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { Globals } from "../../app.constants";
import { Chargement } from "../interfaces/chargement";
import { CritereRecherche } from "../interfaces/critere.recherche";
import { Fichier } from "../interfaces/fichier";
import { Page } from "../interfaces/page";
import { NotificationService } from "./notification.service";

@Injectable({
  providedIn:"root"
})
export class ChargementService{
  /** url de base des webservices chargement */
  private url = environment.apiUrl + '/v1/chargement';
  private _chargements$: BehaviorSubject<Chargement[]> = new BehaviorSubject<Chargement[]>( []);
  private _nbChargements$: BehaviorSubject<number> = new BehaviorSubject<number>(null);
    /** Observable sur chargement. **/
    private _chargement$: BehaviorSubject<Chargement> = new BehaviorSubject<Chargement>( null);

  chargementCourant: Chargement = new Chargement ();
  chargementOriginal: Chargement  = new Chargement ();
  constructor(private http:HttpClient,private notification: NotificationService,private globals: Globals) {
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
  chargementChargementParCritere(critereRecherche:CritereRecherche, scroll?: boolean) {
    this.globals.loading = true;
    return this.http.post<Page<Chargement>>(this.url+"/rechercheBy",critereRecherche)
      .pipe(
        tap((res: Page<Chargement>) => {
          this.setNbChargements(res.totalElements);
          if(critereRecherche.page==0){
            this.setChargements([...res.content])
          }else{
            const result = Array.from(new Set([...this._chargements$.getValue(), ...res.content]));
            this.setChargements(result);
          }
          this.globals.loading=false
        }),

        catchError((err) => {
          this.globals.loading = false;
          this.notification.error(" erreurr de recuperation Chargement ")
          return throwError(() => err)
        })
      )
  }


  /**
   * pour exporter les chargement
   * @param critereRecherche
   */
  exportDocumentChargementParCritere(critereRecherche:CritereRecherche ) {
    this.globals.loading = true;
    return this.http.post<Fichier>(this.url+"/exportDocument",critereRecherche)
      .pipe(
        tap((res:Fichier) => {
          const blob = new Blob([atob(res.content)]);
          saveAs(blob, res.nom);
          this.globals.loading = false;
        }),
        catchError((err) => {
          this.globals.loading = false;
          this.notification.error("erreur de telechargement du fichier ")
          return throwError(() => err)
        })
      )
  }
  exportDocumentChargementParIdChargment(chargements: Chargement[]) {
    this.globals.loading = true;
    return this.http.post<Fichier>(this.url+"/exportDocumentByIDChargement",chargements)
      .pipe(
        tap((res:Fichier) => {
          const blob = new Blob([atob(res.content)]);
          saveAs(blob, res.nom);
          this.globals.loading = false;
        }),
        catchError((err) => {
          this.globals.loading = false;
          this.notification.error("erreur de telechargement du fichier ")
          return throwError(() => err)
        })
      )
  }

  /**
   * modifier Chargement: on ne peut modifier le Produit et la destination
   * @param chargementAmodifier
   */
  modifierChargement(chargementAmodifier: Chargement): Observable<Chargement>{
    return this.http.put<Chargement>(this.url+`/modifier`, chargementAmodifier).pipe(
      share(),
        catchError((err) => {
          this.notification.error(" Erreur lors de la modification de chargement")
          return throwError(() => err)
        }))
  }

  /**
   * getChargementParId
   * @param id id
   * @returns Chargement
   */
  getChargementParId(id: number) {
    return this.http.get<Chargement>(this.url+`/rechercheById/${id}`)
      .pipe(
        tap((res) => {
          this.setChargement(Chargement.fromJson(res,Chargement))
          this.setChargementCourant(Chargement.fromJson(res,Chargement))
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Chargement ")
          return throwError(() => err)
        })
      )
  }

  /**
   * setChargement
   * @param res
   */
  setChargement(res: Chargement) {
    this._chargement$.next(res)
  }

  /**
   * setChargementCourant
   * @param chargement
   */
  setChargementCourant(chargement: Chargement) {
    this.chargementCourant = chargement;
  }

  /**
   * getChargementCourant
   * @returns chargementCourant
   */
  getChargementCourant() {
    return this.chargementCourant;
  }

  supprimer(critereRecherche:CritereRecherche ){
    this.globals.loading = true;
    return this.http.delete<boolean>(this.url+"/supprimerBycritere",{body:critereRecherche })
      .pipe(
        tap((res) => {
          this.globals.loading = false;
          this.notification.success("suppresion avec sucess ")
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Chargement ")
          this.globals.loading = false;
          return throwError(() => err)
        })
     )
  }
  supprimerById(chargements: Chargement[]){
    this.globals.loading = true;
    return this.http.delete<boolean>(this.url+"/supprimerById",{body:chargements })
      .pipe(
        tap((res) => {
          chargements.forEach((chargement)=>{
           this.removeChargement(chargement)
          });
          this.globals.loading = false;
          this.notification.success("suppresion avec sucess ")
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Chargement ")
          this.globals.loading = false;
          return throwError(() => err)
        })
      )
  }
  removeChargement(chargement: Chargement) {
    const currentChargements = this._chargements$.getValue();
    const filtre=currentChargements.find((res)=>res.id==chargement.id)
    const index=currentChargements.indexOf(filtre)
    if(index!=-1){
      currentChargements.splice(index,1)
      this._chargements$.next([...currentChargements]);
    }
  }
  get nbChargements$(){
    return this._nbChargements$.asObservable()
  }

  /** setExploitations */
  setNbChargements(nombre: number ){
    return this._nbChargements$.next(nombre)
  }
}

