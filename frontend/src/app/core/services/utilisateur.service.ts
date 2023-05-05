import {Injectable} from "@angular/core";
import {Globals, SERVER_API_URL} from "../../app.constants";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {BehaviorSubject, catchError, tap, throwError} from "rxjs";
import {Utilisateur} from "../interfaces/utilisateur";

@Injectable({
  providedIn:'root'
})
export class UtilisateurService {
  readonly url =environment.apiUrl
  // @ts-ignore
  // @ts-ignore
  /** Observable sur l'utilisateur connecté. **/
  private _utilisateurConnecte: BehaviorSubject<Utilisateur> = new BehaviorSubject<Utilisateur>( null);
  constructor(private http:HttpClient,private router:Router,
              public globals: Globals) {
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
}
