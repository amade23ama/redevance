import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, Observable, share, tap, throwError} from 'rxjs';
import { environment } from 'src/environments/environment';
import { Vehicule } from '../interfaces/vehicule';
import {Exploitation} from "../interfaces/exploitation";
import {NotificationService} from "./notification.service";
import {Produit} from "../interfaces/produit";
import {Site} from "../interfaces/site";
import {CritereRecherche} from "../interfaces/critere.recherche";

@Injectable({
  providedIn: 'root'
})
/**
 * Service gerant les appels HTTP aux webservices vehicule.
 */

export class VehiculeService {

  /** url de base des webservices vehicule */
  private url = environment.apiUrl + '/v1/vehicule';
  private _vehicules$: BehaviorSubject<Vehicule[]> = new BehaviorSubject<Vehicule[]>( []);
  vehiculeCourant: Vehicule = new Vehicule();
  private _vehicule$: BehaviorSubject<Vehicule> = new BehaviorSubject<Vehicule>(null);
  /** constructor */
  constructor(private httpClient: HttpClient,private notification: NotificationService) { }

  /**
   * appel du service enregistrerVehicules pour définir un véhicule
   * @param vehicule à enregistrer
   * @returns vehicule enregistré
   */
  enregistrerVehicule(vehicule: Vehicule): Observable<Vehicule> {
    return this.httpClient.post<Vehicule>(this.url + '/enregistrer', vehicule)
      .pipe(
        tap((res:Vehicule)=> {
          console.log("l'exploitation est enregistré ", res);
          this.notification.success("le site d'exploitation est enregistré avec sucess")
        }),
        catchError((err) => {
          this.notification.error("erreur enregistrement du  site d'exploitation")
          return throwError(() => err) // RXJS 7+
        })
      );
  }

  /**
   * appel du service rechercherVehicules pour rechercher la liste des véhicules
   * @returns la liste des véhicules
   */
  rechercherVehicules(): Observable<Array<Vehicule>> {
    return this.httpClient.get<Array<Vehicule>>(this.url + '/rechercher').pipe(
      tap((res:Vehicule[])=> {
        this.setVehicules(res)
      }),
      catchError((err) => {
        this.notification.error("erreur de recuperation voiture")
        return throwError(() => err)
      })
    );
  }

  /**
   * appel du service modifierVehicule pour modifier un véhicule
   * @param vehicule à modifier
   * @returns véhicule modifier
   */
  modifierVehicule(vehicule: Vehicule): Observable<Vehicule> {
    return this.httpClient.post<Vehicule>(this.url + '/modifier', vehicule).pipe(share());
  }

  /**
   * appel du service supprimerVehicule pour supprimer un véhicule
   * @param id du véhicule à supprimer
   * @returns true si véhicule supprimer
   */
  supprimerVehicule(vehicule: Vehicule): Observable<boolean> {
    //const param = new HttpParams().set('id',id);
    return this.httpClient.post<boolean>(this.url + '/supprimer', vehicule).pipe(share());
  }
  get vehicules$(): Observable<Vehicule[]> { // getter ou selector
    return this._vehicules$.asObservable()
  }

  setVehicules(res: Vehicule[]) {
    this._vehicules$.next(res)
  }

  /**
   * appel du service rechercherSites pour rechercher la liste des véhicules
   * @returns la liste des Site
   */
  getVehiculeById(id: number){
    return this.httpClient.get<Vehicule>(this.url + `/${id}`)
      .pipe(
        tap((res)=> {
          this.setVehiculeCourant(Vehicule.fromJson(res,Vehicule))
        }),
        catchError((err) => {
          this.notification.error("erreur de chargement du site")
          return throwError(() => err)
        })
      )
  }
  setVehicule(vehicule:Vehicule){
    this._vehicule$.next(vehicule)
  }
  get vehicule$(){
    return this._vehicule$.asObservable()
  }
  setVehiculeCourant(vehicule: Vehicule) {
    this.vehiculeCourant = vehicule
  }
  getVehiculeCourant() {
    return this.vehiculeCourant ;
  }
  chargementVehiculeParCritere(critereRecherche:CritereRecherche ) {
    return this.httpClient.post<Vehicule[]>(this.url+"/rechercheBy",critereRecherche)
      .pipe(
        tap((res:Vehicule[]) => {
          this.setVehicules(res);
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation vehicule ")
          return throwError(() => err)
        })
      )
  }
}
