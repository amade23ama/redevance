import { Injectable } from '@angular/core';

/**
 * Variable globale pour savoir si la page est en cours de chargement.
 * Utile por le spinner
 *
 * @export
 * @class Globals
 */
@Injectable()
export class Globals {
  // Boolean permet de savoir si la page est en cours de chargement
  loading = false;
}

/**
 * Fournit la racine de l'URL pour la construction des requettes
 */
export const SERVER_API_URL = '/redevance';
