import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {SERVER_API_URL} from "../../app.constants";
import {environment} from "../../../environments/environment";
import {HomeCard} from "../interfaces/infotuiles/homeCard";
import {BehaviorSubject, catchError, map, Observable, tap, throwError} from "rxjs";
import {Utilisateur} from "../interfaces/utilisateur";
@Injectable({providedIn: 'root'})
export class TuileService {
  readonly url = environment.apiUrl


  private _infoTuiles$: BehaviorSubject<HomeCard[]> = new BehaviorSubject<HomeCard[]>(null);

  constructor(private http: HttpClient) {
  }

  /**
   * Méthode permettant d'aller interroger l'API pour récupérer les infos-tuiles associé à l'utilisateur connecté.
   */
  getInfosTuiles(): Observable<HomeCard[]> {
    return this.http.get<HomeCard[]>(this.url + '/chargementInfoTuiles')
      .pipe(
        tap((res: HomeCard[]) => {
          console.log(" HomeCard")
          const result = [];
          res.map(a=>HomeCard.fromJson(res, HomeCard))
          this.setInfoTuiles(res)
        }),
        catchError((err) => {
          return throwError(() => err) // RXJS 7+
        })
      )
    /*.pipe(
  map((res: HomeCard[]) => {
     const result = [];
     for (let homecard of res) {
       result.push(HomeCard.fromJson(homecard, HomeCard));
     }
     return result;
   }));
 */
  }

  get infoTuiles$(): Observable<HomeCard[]> { // getter ou selector
    return this._infoTuiles$.asObservable()
  }

  setInfoTuiles(res: HomeCard[]) {
    this._infoTuiles$.next(res)
  }
}
