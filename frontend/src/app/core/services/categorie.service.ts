import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, Observable, tap, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { Globals } from "../../app.constants";
import { Categorie } from "../interfaces/categorie";
import { CritereRecherche } from "../interfaces/critere.recherche";
import { NotificationService } from "./notification.service";

@Injectable({
  providedIn: 'root'
})
export class CategorieService{
  private url = environment.apiUrl + '/v1/categorie';
  private _categories$: BehaviorSubject<Categorie[]> = new BehaviorSubject<Categorie[]>( []);
  categorieCourant: Categorie = new Categorie();
  private _categorie$: BehaviorSubject<Categorie> = new BehaviorSubject<Categorie>(null);
  constructor(private httpClient: HttpClient,private notification: NotificationService,private globals: Globals) { }

  enregistrerCategorie(categorie: Categorie): Observable<Categorie> {
    this.globals.loading = true;
    return this.httpClient.post<Categorie>(this.url + '/enregistrer', categorie).pipe(
      tap((res)=>{
          this.notification.success(" La classe a été enregistrée avec succès ")
          this.setCategorie(Categorie.fromJson(res,Categorie))
          this.globals.loading = false;
        },
        catchError((err) => {
          this.globals.loading = false;
          this.notification.error(" Erreur lors l'enregistrement de la classe ")
          return throwError(() => err)
        })
      )
    );

  }

  getCategorieParId(id: number) {
    return this.httpClient.get<Categorie>(this.url+`/${id}`)
      .pipe(
        tap((res) => {
          this.setCategorie(Categorie.fromJson(res,Categorie))
          this.setCategorieCourant(Categorie.fromJson(res,Categorie))
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Produit ")
          return throwError(() => err) // RXJS 7+
        })
      )
  }

  rechercherCategories() {
    return this.httpClient.get<Categorie[]>(this.url + '/rechercher') .pipe(
      tap((res) => {
        this.setCategories(res)
      }),
      catchError((err) => {
        this.notification.error(" erreurr de recuperation Produit ")
        return throwError(() => err) // RXJS 7+
      })
    )
  }

  /**
   * supprimerProduits
   * @param id categorie
   * @returns
   */
  supprimerCategories(id: number) {
    return this.httpClient.delete<Categorie>(this.url + '/supprimer/'+ id) .pipe(
      tap(() => { }
      ),
      catchError((err) => {
        this.notification.error(" Suppression impossible ")
        return throwError(() => err)
      })
    )
  }

  get categories$(): Observable<Categorie[]> { // getter ou selector
    return this._categories$.asObservable()
  }

  setCategories(res: Categorie[]) {
    this._categories$.next(res)
  }
  get categorie$(): Observable<Categorie> { // getter ou selector
    return this._categorie$.asObservable()
  }

  setCategorie(res: Categorie) {
    this._categorie$.next(res)
  }

  setCategorieCourant(produit: Categorie) {
    this.categorieCourant = produit;
  }
  getCategorieCourant() {
    return this.categorieCourant;
  }
  chargementCategorieParCritere(critereRecherche:CritereRecherche ) {
    this.globals.loading = true;
    return this.httpClient.post<Categorie[]>(this.url+"/rechercheBy",critereRecherche)
      .pipe(
        tap((res:Categorie[]) => {
          this.setCategories(res);
          this.globals.loading = false;
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Utilisateur ")
          this.globals.loading = false;
          return throwError(() => err)
        })
      )
  }
}
