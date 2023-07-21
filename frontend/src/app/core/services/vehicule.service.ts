import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {catchError, Observable, share, tap, throwError} from 'rxjs';
import { environment } from 'src/environments/environment';
import { Vehicule } from '../interfaces/vehicule';
import {Exploitation} from "../interfaces/exploitation";
import {NotificationService} from "./notification.service";

@Injectable({
  providedIn: 'root'
})
/**
 * Service gerant les appels HTTP aux webservices vehicule.
 */

export class VehiculeService {

  /** url de base des webservices vehicule */
  private url = environment.apiUrl + '/v1/vehicule';

  /** constructor */
  constructor(private httpClient: HttpClient,private notification: NotificationService) { }

  /**
   * appel du service enregistrerVehicules pour définir un véhicule
   * @param vehicule à enregistrer
   * @returns vehicule enregistré
   */
  enregistrerVehicule(vehicule: Vehicule): Observable<Vehicule> {
    return this.httpClient.post<Vehicule>(this.url + '/enregistrer', vehicule)
      .pipe(
        tap((res:Vehicule)=> {
          console.log("l'exploitation est enregistré ", res);
          this.notification.success("le site d'exploitation est enregistré avec sucess")
        }),
        catchError((err) => {
          this.notification.error("erreur enregistrement du  site d'exploitation")
          return throwError(() => err) // RXJS 7+
        })
      );
  }

  /**
   * appel du service rechercherVehicules pour rechercher la liste des véhicules
   * @returns la liste des véhicules
   */
  rechercherVehicules(): Observable<Array<Vehicule>> {
    return this.httpClient.get<Array<Vehicule>>(this.url + '/rechercher').pipe(share());
  }

  /**
   * appel du service modifierVehicule pour modifier un véhicule
   * @param vehicule à modifier
   * @returns véhicule modifier
   */
  modifierVehicule(vehicule: Vehicule): Observable<Vehicule> {
    return this.httpClient.post<Vehicule>(this.url + '/modifier', vehicule).pipe(share());
  }

  /**
   * appel du service supprimerVehicule pour supprimer un véhicule
   * @param id du véhicule à supprimer
   * @returns true si véhicule supprimer
   */
  supprimerVehicule(vehicule: Vehicule): Observable<boolean> {
    //const param = new HttpParams().set('id',id);
    return this.httpClient.post<boolean>(this.url + '/supprimer', vehicule).pipe(share());
  }
}
