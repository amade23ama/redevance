export interface Page<T>{
  content: T[];         // Liste des éléments actuels sur la page
  totalPages: number;    // Nombre total de pages
  totalElements: number; // Nombre total d'éléments dans toutes les pages
  last: boolean;         // Dernière page ou non
  first: boolean;        // Première page ou non
  size: number;          // Taille de la page (nombre d'éléments par page)
  number: number;        // Numéro de la page actuelle
  numberOfElements: number; // Nombre d'éléments dans la page actuelle
  empty: boolean;
}
