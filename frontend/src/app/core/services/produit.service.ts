import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, share } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Produit } from '../interfaces/produit';

@Injectable({
  providedIn: 'root'
})
/**
 * Service gerant les appels HTTP aux webservices produit.
 */
@Injectable()
export class ProduitService {

  /** url de base des webservices produit */
  private url = environment.apiUrl + '/v1/produit';

  /** constructor */
  constructor(private httpClient: HttpClient) { }

  /**
   * appel du service enregistrerProduit pour définir un véhicule
   * @param produit à enregistrer
   * @returns produit enregistré
   */
  enregistrerProduit(produit: Produit): Observable<Produit> {
    return this.httpClient.post<Produit>(this.url + '/enregistrer', produit).pipe(share());
  }

  /**
   * appel du service rechercherProduit pour rechercher la liste des véhicules
   * @returns la liste des véhicules
   */
  rechercherProduits(): Observable<Array<Produit>> {
    return this.httpClient.get<Array<Produit>>(this.url + '/rechercher').pipe(share());
  }

  /**
   * appel du service modifierProduit pour modifier un véhicule
   * @param produit à modifier
   * @returns véhicule modifier
   */
  modifierProduit(produit: Produit): Observable<Produit> {
    return this.httpClient.post<Produit>(this.url + '/modifier', produit).pipe(share());
  }

  /**
   * appel du service rechercherProduit pour rechercher la liste des véhicules
   * @returns la liste des véhicules
   */
  compterProduits(): Observable<number> {
    return this.httpClient.get<number>(this.url + '/compter').pipe(share());
  }
}
