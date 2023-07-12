import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, share } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Vehicule } from '../interfaces/vehicule';

@Injectable({
  providedIn: 'root'
})
/**
 * Service gerant les appels HTTP aux webservices vehicule.
 */
@Injectable()
export class VehiculeService {

  /** url de base des webservices vehicule */
  private url = environment.apiUrl + '/v1/vehicule';

  /** constructor */
  constructor(private httpClient: HttpClient) { }

  /**
   * appel du service enregistrerVehicules pour définir un véhicule
   * @param vehicule à enregistrer
   * @returns vehicule enregistré
   */
  enregistrerVehicules(vehicule: Vehicule): Observable<Vehicule> {
    return this.httpClient.post<Vehicule>(this.url + '/enregistrer', vehicule).pipe(share());
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
  supprimerVehicule(id: string): Observable<boolean> {
    const param = new HttpParams().set('id',id);
    return this.httpClient.delete<boolean>(this.url + '/supprimer/'+id, {params: param}).pipe(share());
  }
}
