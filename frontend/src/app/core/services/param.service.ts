import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, catchError, Observable, tap, throwError} from "rxjs";
import {HomeCard} from "../interfaces/infotuiles/homeCard";
import {Profil} from "../interfaces/profil";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn:'root'
})
export class ParamService {
  readonly url = environment.apiUrl
  private _profils$: BehaviorSubject<Profil[]> = new BehaviorSubject<Profil[]>(null);
  constructor(private http:HttpClient) {
}
  get profils$(): Observable<Profil[]> { // getter ou selector
    return this._profils$.asObservable()
  }

  setProfils(res: Profil[]) {
    this._profils$.next(res)
  }
  chargementProfils(): Observable<Profil[]> {
    return this.http.get<Profil[]>(this.url + '/profils')
      .pipe(
        tap((res: Profil[]) => {
           res.map(a=>Profil.fromJson(res, HomeCard))
          this.setProfils(res)
        }),
        catchError((err) => {
          return throwError(() => err) // RXJS 7+
        })
      )
  }
}
