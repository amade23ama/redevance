import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, Observable, share, tap, throwError} from 'rxjs';
import { environment } from 'src/environments/environment';
import { Produit } from '../interfaces/produit';
import {Utilisateur} from "../interfaces/utilisateur";
import {NotificationService} from "./notification.service";
import {Profil} from "../interfaces/profil";
import {CritereRecherche} from "../interfaces/critere.recherche";
import {Exploitation} from "../interfaces/exploitation";

@Injectable({
  providedIn: 'root'
})
/**
 * Service gerant les appels HTTP aux webservices produit.
 */
@Injectable()
export class ProduitService {

  /** url de base des webservices produit */
  private url = environment.apiUrl + '/v1/produit';

  /** Observable sur l'utilisateur connecté. **/
  private _produit$: BehaviorSubject<Produit> = new BehaviorSubject<Produit>( null);
  private _produits$: BehaviorSubject<Produit[]> = new BehaviorSubject<Produit[]>( []);
  produitCourant: Produit = new Produit();
  /** constructor */
  constructor(private httpClient: HttpClient,private notification: NotificationService) { }

  /**
   * appel du service enregistrerProduit pour définir un véhicule
   * @param produit à enregistrer
   * @returns produit enregistré
   */
  enregistrerProduit(produit: Produit): Observable<Produit> {
    return this.httpClient.post<Produit>(this.url + '/enregistrer', produit).pipe(
      tap((res)=>{
        this.notification.success(" enregistrer success ")
        this.setProduit(Produit.fromJson(res,Produit))
      },
        catchError((err) => {
          this.notification.error(" erreur d'enregistrer produit ")
          return throwError(() => err)
        })
        )
    );

  }


  getProduitParId(id: number) {
    return this.httpClient.get<Produit>(this.url+`/${id}`)
      .pipe(
        tap((res) => {
          this.setProduit(Produit.fromJson(res,Produit))
          this.setProduitCourant(Produit.fromJson(res,Produit))
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Produit ")
          return throwError(() => err) // RXJS 7+
        })
      )
  }
  /**
   * appel du service rechercherProduit pour rechercher la liste des véhicules
   * @returns la liste des véhicules
   */
  rechercherProduits(): Observable<Array<Produit>> {
    //return this.httpClient.get<Array<Produit>>(this.url + '/rechercher').pipe(share());
    return this.httpClient.get<Array<Produit>>(this.url + '/rechercher') .pipe(
      tap((res) => {
        this.setProduits(res)
      }),
      catchError((err) => {
        this.notification.error(" erreurr de recuperation Produit ")
        return throwError(() => err) // RXJS 7+
      })
    )
  }

  /**
   * appel du service rechercherProduit pour rechercher la liste des véhicules
   * @returns la liste des véhicules
   */
  compterProduits(): Observable<number> {
    return this.httpClient.get<number>(this.url + '/compter').pipe(share());
  }

  get produits$(): Observable<Produit[]> { // getter ou selector
    return this._produits$.asObservable()
  }

  setProduits(res: Produit[]) {
    this._produits$.next(res)
  }
  get produit$(): Observable<Produit> { // getter ou selector
    return this._produit$.asObservable()
  }

  setProduit(res: Produit) {
    this._produit$.next(res)
  }

  setProduitCourant(produit: Produit) {
    this.produitCourant = produit;
  }
  getProduitCourant() {
    return this.produitCourant;
  }
  chargementProduitParCritere(critereRecherche:CritereRecherche ) {
    return this.httpClient.post<Produit[]>(this.url+"/recherche",critereRecherche)
      .pipe(
        tap((res:Produit[]) => {
          this.setProduits(res);
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Utilisateur ")
          return throwError(() => err)
        })
      )
  }
}
