import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { JwtHelperService } from "@auth0/angular-jwt";
import { catchError, Observable, tap, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { Globals } from "../../app.constants";
import { NotificationService } from "./notification.service";
import { UtilisateurService } from "./utilisateur.service";

export interface TokenObject{
  token:string;
}
const TOKEN_KEY_NAME='authentication_token'
@Injectable({
  providedIn:'root'
})
export  class AuthService {
  readonly url =environment.apiUrl
  //readonly url='https://reqres.in/api'
  static readonly BEARER = 'Bearer ';
  // nom de la clef contenant le token JWT cas
  private readonly TOKEN_KEY_NAME = 'authentication_token';

  // nom de la clef contenant le user actuel
  private readonly USER_KEY_NAME = 'currentUser';

  // nom de la clef contenant les droits de l`utilisateur
  private readonly ROLE_KEY_NAME = 'droits';
  _droits: string[] =[];

  // nom de la clef contenant la cle de renouvellement
  private readonly RENEW_TOKEN_KEY_NAME = 'key';

  // type de mémoire de stockage du ticket (sessionStorage ou localStorage)
  private readonly stockage = sessionStorage;
  /**
   * Jwt Helper pour le décodage du token JWT
   *
   * @memberof AuthenticationService
   */
  jwtHelper = new JwtHelperService();
  constructor(private http:HttpClient,private router:Router,
              private utilisateurService: UtilisateurService,
              public globals: Globals,private notification: NotificationService) {
  }
  login(payload: { login: string, password: string }): Observable<TokenObject> {
    return this.http.post<TokenObject>(this.url+"/login", payload)
  }
  getToken(){
    return this.stockage.getItem(this.TOKEN_KEY_NAME);
  }
  setToken(val: string) {
    this.stockage.setItem(this.TOKEN_KEY_NAME, val)
  }
  logout():Observable<any>{

    return this.http.get<any>(this.url+"/logout")
      .pipe(
        tap((res:any) => {
          this.stockage.clear();
        }),
        catchError((err) => {
          return throwError(() => err) // RXJS 7+
        })
      )
  }
  clearSession() {
    this.stockage.clear();
  }
  /**
   * test si l'utilisateur courant est connecté
   * @returns
   * @memberof AuthenticationService
   */
  isLoggedIn() {
    return !!this.stockage.getItem(this.TOKEN_KEY_NAME);
  }
  storeAuthenticationToken(jwt: any) {
    this.setToken(jwt);
  }

  /**
   *
   *
   * @param {string} token
   * @returns {*}
   * @memberof AuthenticationService
   */
  getDecodedAccessToken(token: string): any {
    try {
      return this.jwtHelper.decodeToken(token);
    } catch (Error) {
      console.log('error : ' + Error);
    }
  }
  authenticate( payload: { login: string, password: string }): Observable<TokenObject>  {
    return this.http.post<TokenObject>(this.url+"/login", payload)
      .pipe(
        tap((res: TokenObject) => {
          //const bearerToken = resp.headers.get(AuthenticationService.JWT_HEADER);
          // Recuperation du token JWT depuis l'header
          this.notification.success("connection succes")
          const token = this.authenticateSuccess(res);
        }),
        catchError((err) => {
          this.notification.error("Mot de passe invalide")
          return throwError(() => err)
        })
      )
  }
  authenticateSuccess(res: TokenObject){
    this.storeAuthenticationToken(res.token)
    const tokendecrypter = this.getDecodedAccessToken(res.token);
    this.droits = tokendecrypter.authorities;
    const tokenInfo = res.token;
    this.majUserConnecte();
    return tokenInfo;
  }
  private majUserConnecte() {
    this.utilisateurService.updateConnected().subscribe();
  }
  /**
   *
   * @param {String} droit
   * @returns {boolean}
   */
  hasDroits(droit: string): boolean {
    return this.droits.includes(droit);
  }
  get droits() {
    const value = this.stockage.getItem(this.ROLE_KEY_NAME);
    const droits = value ? JSON.parse(value) : null;
    if (droits) {
      this._droits = droits;
    }
    return this._droits;
  }

  set droits(newDroit: string[]) {
    this._droits = newDroit;
    this.stockage.setItem(this.ROLE_KEY_NAME, JSON.stringify(this._droits));
  }

  /*hasAnyDroits(multipleDroits: string[]): boolean {
    let hasAuMoinsUnDroit = false;
    if (multipleDroits !== undefined) {
      multipleDroits.forEach(dr => {
        if (this.droits !== undefined) {
          hasAuMoinsUnDroit = hasAuMoinsUnDroit || this.droits.includes(dr);
        }
      });
    }

    return hasAuMoinsUnDroit;
  }
  */
  hasAnyDroits(multipleDroits: string[]){
    return multipleDroits.some(role => this.droits.includes(role));
  }

  /**
   * reset pwd
   * @param data 
   * @returns  boolean
   */
  reset(data: { login: string, email: string }): Observable<boolean> {
    console.log("data", data);
    return this.http.post<boolean>(this.url+"/reset", data).pipe(
      tap((res) => {}),
      catchError((err) => {
        this.notification.error("Le compte n'existe pas. Veuillez contacter l'administrateur du site.")
        return throwError(() => err)
      })
    )
  }

}
