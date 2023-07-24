import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Exploitation} from "../interfaces/exploitation";
import {catchError, tap, throwError} from "rxjs";
import {NotificationService} from "./notification.service";

@Injectable({
  providedIn:"root"
})
export class DepotService{
  /** url de base des webservices produit */
  private url = environment.apiUrl + '/v1/depot';
  constructor(private http:HttpClient,private notification: NotificationService) {
  }
  creerDepot(formData: FormData){
    return this.http.post<any>(this.url + '/creer', formData).pipe(
      tap((res:any)=> {
        console.log("depot en cours de creation ", res);
        this.notification.success("depot en cours de creation")
      }),
      catchError((err) => {
        this.notification.error("erreur de creation de depot")
        return throwError(() => err)
      })
    );
  }
}
