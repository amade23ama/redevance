import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {SERVER_API_URL} from "../../app.constants";
import {environment} from "../../../environments/environment";
import {HomeCard} from "../interfaces/infotuiles/homeCard";
import {catchError, map, Observable, tap, throwError} from "rxjs";
@Injectable({providedIn: 'root'})
export class TuileService {
  readonly url =environment.apiUrl
  constructor(private http:HttpClient) {
  }
  /**
   * Méthode permettant d'aller interroger l'API pour récupérer les infos-tuiles associé à l'utilisateur connecté.
   */
  getInfosTuiles(): Observable<HomeCard[]> {
    return this.http.get<HomeCard[]>(this.url + '/api/chargementInfoTuiles')
      .pipe(
        tap((res: HomeCard[]) => {
          console.log(" HomeCard")
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
}
