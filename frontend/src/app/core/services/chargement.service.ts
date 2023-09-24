import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {BehaviorSubject, catchError, tap, throwError} from "rxjs";
import {Chargement} from "../interfaces/chargement";
import {NotificationService} from "./notification.service";
import {CritereRecherche} from "../interfaces/critere.recherche";
import {saveAs} from "file-saver";
import {Fichier} from "../interfaces/fichier";

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
  chargementChargementParCritere(critereRecherche:CritereRecherche ) {
    return this.http.post<Chargement[]>(this.url+"/rechercheBy",critereRecherche)
      .pipe(
        tap((res:Chargement[]) => {
          this.setChargements(res!==null?res:[]);
        }),
        catchError((err) => {
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

    return this.http.post<Fichier>(this.url+"/exportDocument",critereRecherche)
      .pipe(
        tap((res:Fichier) => {
          const blob = new Blob([atob(res.content)]);
          saveAs(blob, res.nom);
        }),
        catchError((err) => {
          this.notification.error("erreur de telechargement du fichier ")
          return throwError(() => err)
        })
      )
  }
}

