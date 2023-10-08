import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AbstractControl } from "@angular/forms";
import { Router } from "@angular/router";
import * as clone from 'lodash';
import { BehaviorSubject, catchError, lastValueFrom, tap, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { Globals } from "../../app.constants";
import { CritereRecherche } from "../interfaces/critere.recherche";
import { Utilisateur } from "../interfaces/utilisateur";
import { NotificationService } from "./notification.service";
@Injectable({
  providedIn:'root'
})
export class UtilisateurService {
  readonly url =environment.apiUrl
  /** Observable sur l'utilisateur connecté. **/
  private _utilisateurConnecte: BehaviorSubject<Utilisateur> = new BehaviorSubject<Utilisateur>( null);
  private _utilisateurs: BehaviorSubject<Utilisateur[]> = new BehaviorSubject<Utilisateur[]>( []);

  utilisateurCourant: Utilisateur = new Utilisateur();
  utilisateurOriginal: Utilisateur = new Utilisateur();
  constructor(private http:HttpClient,private router:Router,
              public globals: Globals,private notification: NotificationService,) {
  }
  updateConnected(){
    return this.http.get<Utilisateur>(this.url+"/updateConnected")
      .pipe(
      tap((res:Utilisateur) => {
        console.log(" deconnexion 2")
        this.setUtilisateurConnecte(Utilisateur.fromJson(res, Utilisateur));
      }),
      catchError((err) => {
        return throwError(() => err) // RXJS 7+
      })
    )
  }

  /**
   * Méthode permettant de récupérer l'utilisateur connecté via l'observable.
   */
  getUtilisateurConnecte(): BehaviorSubject<Utilisateur> {
    return this._utilisateurConnecte;
  }

  /**
   * Méthode permettant de mettre à jour l'observable contenant l'utilisateur connecté.
   * @param value
   */
  setUtilisateurConnecte(value: Utilisateur) {
    this._utilisateurConnecte.next(value);
  }

  async checkEmail(input: AbstractControl): Promise<null | { emailExists: boolean }> {
    const email = input.value;
    const url = `${this.url}/users/${encodeURIComponent(email)}`;
    const user = await lastValueFrom(this.http.get<Utilisateur>(this.url +`/utilisateur/${email}`))
    return input.value == user?.email ? { emailExists: true } : null
  }
  sauvegarder(utilsateur:Utilisateur){
    return this.http.post<Utilisateur>(this.url+"/utilisateur/enregistrer",utilsateur)
      .pipe(
        tap((res:Utilisateur) => {
          this.notification.success(" Utilisateur créé ")
          //this.setUtilisateurConnecte(Utilisateur.fromJson(res, Utilisateur));
        }),
        catchError((err) => {
          this.notification.error(" erreurr de creation Utilisateur ")
          return throwError(() => err)
        })
      )
  }
  getAllUtilisateur(){
    return this.http.get<Utilisateur[]>(this.url+"/utilisateur/users")
      .pipe(
        tap((res:Utilisateur[]) => {
          this.setUtilisateurs(res);
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Utilisateur ")
          return throwError(() => err) // RXJS 7+
        })
      )
  }
  getUtilisateurParId(id: number) {
    return this.http.get<Utilisateur>(this.url+`/utilisateur/get/${id}`)
      .pipe(
        tap((utilisteur:Utilisateur) => {
          this.setUtilisateurOriginal(utilisteur)
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Utilisateur ")
          return throwError(() => err) // RXJS 7+
        })
      )
  }
  get utilisateurs$(){
    return this._utilisateurs.asObservable()
  }
  setUtilisateurs(utilisateurs:Utilisateur[] ){
    return this._utilisateurs.next(utilisateurs)
  }

  /**
   * Cloner le Utilisateur original
   */
  public clonerUtilisateurOriginal() {
    this.utilisateurCourant= clone.cloneDeep(this.utilisateurOriginal );
  }
  /**
   * Stocke le Utilisateur passée en paramètre dans UtilisateurOriginal et copie les valeurs dans UtilisateurCourant
   * @param contrat
   */
  setUtilisateurOriginal(utilisateur: Utilisateur) {
    this.utilisateurOriginal = utilisateur;
    this.clonerUtilisateurOriginal();
  }

  setUtilisateurCourant(utilisateur: Utilisateur) {
    this.utilisateurCourant = utilisateur;
  }
  getUtilisateurCourant() {
    return this.utilisateurCourant;
  }
  /**
   * retourne le Utilisateur original
   */
  getUtilisateurOriginal(){
    return clone.cloneDeep(this.utilisateurOriginal);
  }

  /**
   * Restaurer le Utilisateur courant qui reprend ses valeurs avant les modifications
   */
  restaurerUtilisateurCourant() {
    this.clonerUtilisateurOriginal();
  }

  /**
   * Permet de savoir si le Utilisateur courant a été modifié.
   * @return true si le Utilisateur courant a été modifié
   */
  isUtilisateurModifie(): boolean {

    // Si la déclaration courante n'existe pas
    if (!this.utilisateurCourant) {
      return false;
    }
    // En création, la déclaration est considérée comme étant modifié
    if (!this.utilisateurCourant.id) {
      return true;
    }

    // todo : faut-il trier le journal de changememts avant de comaprer ?

    const utilisateurCourant: Utilisateur = clone.cloneDeep(this.utilisateurCourant);
    return !clone.isEqual(utilisateurCourant, this.utilisateurOriginal);
  }

  /**
   * Permet de savoir si le Utilisateur courant a été persisté
   * @private
   */
  private isUtilisateurSauvegarde() {
    return this.getUtilisateurCourant().id !== null;
  }

  /**
   * Permet de remettre à zéro e Utilisateur Courant et le Utilisateur Original
   * @private
   */
  public purgerUtilisateur() {
    // purge le Utilisateur courant et le contrat original
    this.setUtilisateurOriginal(new Utilisateur());
  }
  private chargerNouveauUtilisateur() {
    if (this.isUtilisateurSauvegarde()) {
      //this.supprimerUtilisateur(this.getUtilisateurCourant());
    }
    this.purgerUtilisateur();
  }

  async emailCheck(input: AbstractControl,id:number): Promise<null | { emailExists: boolean }> {
    const email = input.value;
    let urlapi=this.url
    if(id){
      urlapi=urlapi.concat(`/utilisateur/exist/${id}/${email}`)
    }else{
      urlapi=urlapi.concat(`/utilisateur/exist/${email}`)
    }
    const status = await lastValueFrom(this.http.get<boolean>( urlapi))
    return status ? { emailExists: true } : null
  }
  enregistrer(utilsateur:Utilisateur){
    return this.http.put<Utilisateur>(this.url+"/utilisateur/enregistrer",utilsateur)
      .pipe(
        tap((res:Utilisateur) => {
          this.notification.success(" enregistrer success ")
          this.setUtilisateurOriginal(Utilisateur.fromJson(res,Utilisateur))
          return Utilisateur.fromJson(res,Utilisateur)
        }),
        catchError((err) => {
          this.notification.error(" erreur d'enregistrer Utilisateur ")
          return throwError(() => err)
        })
      )
  }
  async checkLogin(input: AbstractControl){
    let urlapi=this.url
    const login=input.value
    const status = await lastValueFrom(this.http.get<boolean>( this.url+`/login/${login}`))
    return status ? null:{ emailExists: true }
  }
  chargementUtilisateurParCritere(critereRecherche:CritereRecherche ) {
    return this.http.post<Utilisateur[]>(this.url+"/utilisateur/recherche",critereRecherche)
      .pipe(
        tap((res:Utilisateur[]) => {
          this.setUtilisateurs(res);
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Utilisateur ")
          return throwError(() => err)
        })
      )

  }
  
  /** checkActive */
  async checkActive(input: AbstractControl){
    const login=input.value
    const status = await lastValueFrom(this.http.get<boolean>( this.url+`/active/${login}`))
    return status ? null : { isActive: true };
  }
}

