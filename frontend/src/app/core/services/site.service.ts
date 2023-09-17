import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Site } from '../interfaces/site';
import {NotificationService} from "./notification.service";
import {Produit} from "../interfaces/produit";
import {CritereRecherche} from "../interfaces/critere.recherche";

@Injectable({
  providedIn: 'root'
})
/**
 * Service gerant les appels HTTP aux webservices site.
 */
export class SiteService {

  /** les sites trouvés */
  private _sites: BehaviorSubject<Site[]> = new BehaviorSubject<Site[]>( []);

   /** le nombre de site */
   private _nbSites: BehaviorSubject<number> = new BehaviorSubject<number>(null);

   /** les sites trouvés by id */
  private _siteById: BehaviorSubject<Site> = new BehaviorSubject<Site>(null);

   /** le site enregistrer */
   private _siteEnregistrer: BehaviorSubject<Site> = new BehaviorSubject<Site>(null);

  /** url de base des webservices site */
  private url = environment.apiUrl + '/v1/site';
  siteCourant: Site = new Site();
  constructor(private httpClient: HttpClient,private notification: NotificationService) { }

  /**
   * appel du service rechercherSites pour rechercher la liste des véhicules
   * @returns la liste des Site
   */
  getAllSites(){
    return this.httpClient.get<Site[]>(this.url + '/rechercher')
    .pipe(
      tap((res:Site[])=> {
        this.setSites(res);
      }),
      catchError((err) => {
        return throwError(() => err) // RXJS 7+
      })
    )
  }

  /** la liste des sites de dépot */
  get sites$(){
    return this._sites.asObservable()
  }

  /** setSites */
  setSites(sites:Site[] ){
    return this._sites.next(sites)
  }

  /**
   * appel du service compterSite
   * @returns la nombre de site
   */
  getCompteurSites(){
    return this.httpClient.get<number>(this.url + '/compter')
    .pipe(
      tap((res:number)=> {
        this.setNbSites(res);
      }),
      catchError((err) => {
        return throwError(() => err)
      })
    )
  }

  /** la liste des sites de dépot */
  get nbSites$(){
    return this._nbSites.asObservable()
  }

  /** setSites */
  setNbSites(nombre:number ){
    return this._nbSites.next(nombre)
  }

  /**
   * appel du service rechercherSites pour rechercher la liste des véhicules
   * @returns la liste des Site
   */
  getSiteById(id: number){
    return this.httpClient.get<Site>(this.url + `/${id}`)
    .pipe(
      tap((res)=> {
        this.setSiteCourant(Site.fromJson(res,Site))
      }),
      catchError((err) => {
        this.notification.error("erreur de chargement du site")
        return throwError(() => err)
      })
    )
  }

  /** la liste des sites de dépot */
  get siteById$(){
    return this._siteById.asObservable()
  }

  /** setSites */
  setSiteById(site:Site){
    return this._siteById.next(site)
  }

  /**
   * appel du service rechercherSites pour rechercher la liste des véhicules
   * @returns la liste des Site
   */
  supprimerSite(id: number){
    return this.httpClient.get<boolean>(this.url + `/supprimer/${id}`)
    .pipe(
      tap((res:boolean)=> {
        console.log("suppression du site d'id: ", id);
      }),
      catchError((err) => {
        return throwError(() => err)
      })
    )
  }

  /**
   * appel du service enregistrersite pour définir un site
   * @param site à enregistrer
   * @returns site enregistré
   */
  enregistrerSite(site: Site){
    return this.httpClient.post<Site>(this.url + '/enregistrer', site).pipe(
      tap((res:Site)=> {
        console.log("le site est enregistré ", res);
        this.notification.success("le site est enregistré  avec succes")
        this.setSiteEnregistrer(res);
      }),
      catchError((err) => {
        this.notification.error("erreur d'enregistement du site")
        return throwError(() => err)
      })
    );
  }

  /** siteEnregistre */
  get siteEnregistre$(){
    return this._siteEnregistrer.asObservable()
  }

  /** setSiteEnregistrer */
  setSiteEnregistrer(site:Site){
    return this._siteEnregistrer.next(site)
  }
  setSiteCourant(site: Site) {
    this.siteCourant = site
  }
  getSiteCourant() {
    return this.siteCourant;
  }
  chargementSiteParCritere(critereRecherche:CritereRecherche ) {
    return this.httpClient.post<Site[]>(this.url+"/rechercheBy",critereRecherche)
      .pipe(
        tap((res:Site[]) => {
          this.setSites(res);
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Utilisateur ")
          return throwError(() => err)
        })
      )
  }
}
