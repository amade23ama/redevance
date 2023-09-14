import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {environment} from "../../../environments/environment";
import {BehaviorSubject, catchError, of, tap, throwError} from "rxjs";
import {AutocompleteRecherche} from "../interfaces/autocomplete.recherche";
import {Site} from "../interfaces/site";
import {NotificationService} from "./notification.service";
@Injectable({
  providedIn:"root"
})
export class AutocompleteRechercheService{
  readonly url = environment.apiUrl+"/v1/autocomplete"
  private _autoCompleteRecherches$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);
  private _critereRecherches$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);

  constructor(private readonly httpClient:HttpClient,
              private notification: NotificationService) {
  }
  autocompleteUtilisateur(capture:string){
    if(capture==''){
      this.setAutoCompleteRecherches([])
      return of();
    }
    return this.httpClient.get<AutocompleteRecherche[]>(this.url + `/utilisateur/${capture}`)
      .pipe(
        tap((res)=> {
          console.log("ama")
          //AutocompleteRecherche.fromJson(res,AutocompleteRecherche)
          this.setAutoCompleteRecherches(res!=null ?res:[])
        }),
        catchError((err) => {
          this.setAutoCompleteRecherches([])

          this.notification.error("erreur de recherche")
          return throwError(() => err)
        })
      )
  }
  setAutoCompleteRecherches(autocompleteRecherches:AutocompleteRecherche[]){
    return this._autoCompleteRecherches$.next(autocompleteRecherches)
  }
  get autoCompleteRecherches$(){
    return this._autoCompleteRecherches$.asObservable()
  }
  addAutocompleteRecherche(autocompleteRecherche:AutocompleteRecherche) {
    const currentList = this._critereRecherches$.getValue();
    if(!currentList.find((res)=>res.id==autocompleteRecherche.id)){
      const updatedList = [...currentList, autocompleteRecherche];
      this._critereRecherches$.next(updatedList);
    }
  }

  removeAutocompleteRecherche(autocompleteRecherche:AutocompleteRecherche) {
    const currentAutoComplete = this._critereRecherches$.getValue();
    const filtre=currentAutoComplete.find((res)=>res.id==autocompleteRecherche.id)
    const index=currentAutoComplete.indexOf(filtre)
    if(index!=-1){
      currentAutoComplete.splice(index,1)
      this._critereRecherches$.next(currentAutoComplete);
    }
  }
  get critereRecherches$(){
    return this._critereRecherches$.asObservable()
  }

}
