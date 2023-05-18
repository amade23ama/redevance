import {Injectable} from "@angular/core";
import {Globals, SERVER_API_URL} from "../../app.constants";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {BehaviorSubject, catchError, lastValueFrom, tap, throwError} from "rxjs";
import {Utilisateur} from "../interfaces/utilisateur";
import {AbstractControl} from "@angular/forms";
import {NotificationService} from "./notification.service";

@Injectable({
  providedIn:'root'
})
export class UtilisateurService {
  readonly url =environment.apiUrl
  /** Observable sur l'utilisateur connecté. **/
  private _utilisateurConnecte: BehaviorSubject<Utilisateur> = new BehaviorSubject<Utilisateur>( null);
  private _utilisateurs: BehaviorSubject<Utilisateur[]> = new BehaviorSubject<Utilisateur[]>( []);
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
  get utilisateurs$(){
    return this._utilisateurs.asObservable()
  }
  setUtilisateurs(utilisateurs:Utilisateur[] ){
    return this._utilisateurs.next(utilisateurs)
  }
}

