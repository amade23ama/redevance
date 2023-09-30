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


  private _autoCompleteRecherchesDepot$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);
  private _critereRecherchesDepot$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);

  private _autoCompleteRecherchesSite$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);
  private _critereRecherchesSite$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);

  private _autoCompleteRecherchesProduit$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);
  private _critereRecherchesProduit$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);

  private _autoCompleteRecherchesExploitation$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);
  private _critereRecherchesExploitation$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);

  private _autoCompleteRecherchesChargement$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);
  private _critereRecherchesChargement$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);

  private _autoCompleteRecherchesVehicule$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);
  private _critereRecherchesVehicule$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);


  private _autoCompleteRecherchesCategorie$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);
  private _critereRecherchesCategorie$: BehaviorSubject<AutocompleteRecherche[]> = new BehaviorSubject<AutocompleteRecherche[]>([]);

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

  autocompleteDepot(capture:string){
    if(capture==''){
      this.setAutoCompleteRecherchesDepot([])
      return of();
    }
    return this.httpClient.get<AutocompleteRecherche[]>(this.url + `/depot/${capture}`)
      .pipe(
        tap((res)=> {
          this.setAutoCompleteRecherchesDepot(res!=null ?res:[])
        }),
        catchError((err) => {
          this.setAutoCompleteRecherchesDepot([])

          this.notification.error("erreur de recherche")
          return throwError(() => err)
        })
      )
  }
  autocompleteSiteExploitation(capture:string){
    if(capture==''){
      this.setAutoCompleteRecherchesExploitation([])
      return of();
    }
    return this.httpClient.get<AutocompleteRecherche[]>(this.url + `/sitesExploitation/${capture}`)
      .pipe(
        tap((res)=> {
          this.setAutoCompleteRecherchesExploitation(res!=null ?res:[])
        }),
        catchError((err) => {
          this.setAutoCompleteRecherchesExploitation([])

          this.notification.error("erreur de recherche")
          return throwError(() => err)
        })
      )
  }
  autocompleteSite(capture:string){
    if(capture==''){
      this.setAutoCompleteRecherchesSite([])
      return of();
    }
    return this.httpClient.get<AutocompleteRecherche[]>(this.url + `/sitesPessage/${capture}`)
      .pipe(
        tap((res)=> {
          this.setAutoCompleteRecherchesSite(res!=null ?res:[])
        }),
        catchError((err) => {
          this.setAutoCompleteRecherchesSite([])

          this.notification.error("erreur de recherche")
          return throwError(() => err)
        })
      )
  }
  autocompleteProduit(capture:string){
    if(capture==''){
      this.setAutoCompleteRecherchesProduit([])
      return of();
    }
    return this.httpClient.get<AutocompleteRecherche[]>(this.url + `/produit/${capture}`)
      .pipe(
        tap((res)=> {
          this.setAutoCompleteRecherchesProduit(res!=null ?res:[])
        }),
        catchError((err) => {
          this.setAutoCompleteRecherchesProduit([])

          this.notification.error("erreur de recherche")
          return throwError(() => err)
        })
      )
  }
  autocompleteVehicule(capture:string){
    if(capture==''){
      this.setAutoCompleteRecherchesVehicule([])
      return of();
    }
    return this.httpClient.get<AutocompleteRecherche[]>(this.url + `/voitures/${capture}`)
      .pipe(
        tap((res)=> {
          this.setAutoCompleteRecherchesVehicule(res!=null ?res:[])
        }),
        catchError((err) => {
          this.setAutoCompleteRecherchesVehicule([])

          this.notification.error("erreur de recherche")
          return throwError(() => err)
        })
      )
  }
  autocompleteChargement(capture:string){
    if(capture==''){
      this.setAutoCompleteRecherchesChargement([])
      return of();
    }
    return this.httpClient.get<AutocompleteRecherche[]>(this.url + `/chargement/${capture}`)
      .pipe(
        tap((res)=> {
          this.setAutoCompleteRecherchesChargement(res!=null ?res:[])
        }),
        catchError((err) => {
          this.setAutoCompleteRecherchesChargement([])

          this.notification.error("erreur de recherche")
          return throwError(() => err)
        })
      )
  }
  autocompleteCategorie(capture:string){
    if(capture==''){
      this.setAutoCompleteRecherchesCategorie([])
      return of();
    }
    return this.httpClient.get<AutocompleteRecherche[]>(this.url + `/categorie/${capture}`)
      .pipe(
        tap((res)=> {
          this.setAutoCompleteRecherchesCategorie(res!=null ?res:[])
        }),
        catchError((err) => {
          this.setAutoCompleteRecherchesCategorie([])

          this.notification.error("erreur de recherche")
          return throwError(() => err)
        })
      )
  }
  setAutoCompleteRecherchesChargement(autocompleteRecherches:AutocompleteRecherche[]){
    return this._autoCompleteRecherchesChargement$.next(autocompleteRecherches)
  }
  get autoCompleteRecherchesChargement$(){
    return this._autoCompleteRecherchesChargement$.asObservable()
  }
  setAutoCompleteRecherchesDepot(autocompleteRecherches:AutocompleteRecherche[]){
    return this._autoCompleteRecherchesDepot$.next(autocompleteRecherches)
  }
  get autoCompleteRecherchesDepot$(){
    return this._autoCompleteRecherchesDepot$.asObservable()
  }
  setAutoCompleteRecherchesSite(autocompleteRecherches:AutocompleteRecherche[]){
    return this._autoCompleteRecherchesSite$.next(autocompleteRecherches)
  }
  get autoCompleteRecherchesSite$(){
    return this._autoCompleteRecherchesSite$.asObservable()
  }
  setAutoCompleteRecherchesExploitation(autocompleteRecherches:AutocompleteRecherche[]){
    return this._autoCompleteRecherchesExploitation$.next(autocompleteRecherches)
  }
  get autoCompleteRecherchesExploitation$(){
    return this._autoCompleteRecherchesExploitation$.asObservable()
  }
  setAutoCompleteRecherchesProduit(autocompleteRecherches:AutocompleteRecherche[]){
    return this._autoCompleteRecherchesProduit$.next(autocompleteRecherches)
  }
  get autoCompleteRecherchesProduit$(){
    return this._autoCompleteRecherchesProduit$.asObservable()
  }
  setAutoCompleteRecherchesVehicule(autocompleteRecherches:AutocompleteRecherche[]){
    return this._autoCompleteRecherchesVehicule$.next(autocompleteRecherches)
  }
  get autoCompleteRecherchesVehicule$(){
    return this._autoCompleteRecherchesVehicule$.asObservable()
  }
  addAutocompleteRechercheChargement(autocompleteRecherche:AutocompleteRecherche) {
    const currentList = this._critereRecherchesChargement$.getValue();
    if(!currentList.find((res)=>res.id==autocompleteRecherche.id)){
      const updatedList = [...currentList, autocompleteRecherche];
      this._critereRecherchesChargement$.next(updatedList);
    }
  }
  removeAutocompleteRechercheChargement(autocompleteRecherche:AutocompleteRecherche) {
    const currentAutoComplete = this._critereRecherchesChargement$.getValue();
    const filtre=currentAutoComplete.find((res)=>res.id==autocompleteRecherche.id)
    const index=currentAutoComplete.indexOf(filtre)
    if(index!=-1){
      currentAutoComplete.splice(index,1)
      this._critereRecherchesChargement$.next(currentAutoComplete);
    }
  }
  get critereRecherchesChargement$(){
    return this._critereRecherchesChargement$.asObservable()
  }
  addAutocompleteRechercheDepot(autocompleteRecherche:AutocompleteRecherche) {
    const currentList = this._critereRecherchesDepot$.getValue();
    if(!currentList.find((res)=>res.id==autocompleteRecherche.id)){
      const updatedList = [...currentList, autocompleteRecherche];
      this._critereRecherchesDepot$.next(updatedList);
    }
  }
  removeAutocompleteRechercheDepot(autocompleteRecherche:AutocompleteRecherche) {
    const currentAutoComplete = this._critereRecherchesDepot$.getValue();
    const filtre=currentAutoComplete.find((res)=>res.id==autocompleteRecherche.id)
    const index=currentAutoComplete.indexOf(filtre)
    if(index!=-1){
      currentAutoComplete.splice(index,1)
      this._critereRecherchesDepot$.next(currentAutoComplete);
    }
  }
  get critereRecherchesDepot$(){
    return this._critereRecherchesDepot$.asObservable()
  }
  addAutocompleteRechercheSite(autocompleteRecherche:AutocompleteRecherche) {
    const currentList = this._critereRecherchesSite$.getValue();
    if(!currentList.find((res)=>res.id==autocompleteRecherche.id)){
      const updatedList = [...currentList, autocompleteRecherche];
      this._critereRecherchesSite$.next(updatedList);
    }
  }
  removeAutocompleteRechercheSite(autocompleteRecherche:AutocompleteRecherche) {
    const currentAutoComplete = this._critereRecherchesSite$.getValue();
    const filtre=currentAutoComplete.find((res)=>res.id==autocompleteRecherche.id)
    const index=currentAutoComplete.indexOf(filtre)
    if(index!=-1){
      currentAutoComplete.splice(index,1)
      this._critereRecherchesSite$.next(currentAutoComplete);
    }
  }
  get critereRecherchesSite$(){
    return this._critereRecherchesSite$.asObservable()
  }
  addAutocompleteRechercheExploitation(autocompleteRecherche:AutocompleteRecherche) {
    const currentList = this._critereRecherchesExploitation$.getValue();
    if(!currentList.find((res)=>res.id==autocompleteRecherche.id)){
      const updatedList = [...currentList, autocompleteRecherche];
      this._critereRecherchesExploitation$.next(updatedList);
    }
  }
  removeAutocompleteRechercheExploitation(autocompleteRecherche:AutocompleteRecherche) {
    const currentAutoComplete = this._critereRecherchesExploitation$.getValue();
    const filtre=currentAutoComplete.find((res)=>res.id==autocompleteRecherche.id)
    const index=currentAutoComplete.indexOf(filtre)
    if(index!=-1){
      currentAutoComplete.splice(index,1)
      this._critereRecherchesExploitation$.next(currentAutoComplete);
    }
  }
  get critereRecherchesExploitation$(){
    return this._critereRecherchesExploitation$.asObservable()
  }
  addAutocompleteRechercheProduit(autocompleteRecherche:AutocompleteRecherche) {
    const currentList = this._critereRecherchesProduit$.getValue();
    if(!currentList.find((res)=>res.id==autocompleteRecherche.id)){
      const updatedList = [...currentList, autocompleteRecherche];
      this._critereRecherchesProduit$.next(updatedList);
    }
  }
  removeAutocompleteRechercheProduit(autocompleteRecherche:AutocompleteRecherche) {
    const currentAutoComplete = this._critereRecherchesProduit$.getValue();
    const filtre=currentAutoComplete.find((res)=>res.id==autocompleteRecherche.id)
    const index=currentAutoComplete.indexOf(filtre)
    if(index!=-1){
      currentAutoComplete.splice(index,1)
      this._critereRecherchesProduit$.next(currentAutoComplete);
    }
  }
  get critereRecherchesProduit$(){
    return this._critereRecherchesProduit$.asObservable()
  }
  get critereRecherchesVehicule$(){
    return this._critereRecherchesVehicule$.asObservable()
  }
  addAutocompleteRechercheVehicule(autocompleteRecherche:AutocompleteRecherche) {
    const currentList = this._critereRecherchesVehicule$.getValue();
    if(!currentList.find((res)=>res.id==autocompleteRecherche.id)){
      const updatedList = [...currentList, autocompleteRecherche];
      this._critereRecherchesVehicule$.next(updatedList);
    }
  }
  removeAutocompleteRechercheVehicule(autocompleteRecherche:AutocompleteRecherche) {
    const currentAutoComplete = this._critereRecherchesVehicule$.getValue();
    const filtre=currentAutoComplete.find((res)=>res.id==autocompleteRecherche.id)
    const index=currentAutoComplete.indexOf(filtre)
    if(index!=-1){
      currentAutoComplete.splice(index,1)
      this._critereRecherchesVehicule$.next(currentAutoComplete);
    }
  }

  get critereRecherchesCategorie$(){
    return this._critereRecherchesCategorie$.asObservable()
  }
  addAutocompleteRechercheCategorie(autocompleteRecherche:AutocompleteRecherche) {
    const currentList = this._critereRecherchesCategorie$.getValue();
    if(!currentList.find((res)=>res.id==autocompleteRecherche.id)){
      const updatedList = [...currentList, autocompleteRecherche];
      this._critereRecherchesCategorie$.next(updatedList);
    }
  }
  removeAutocompleteRechercheCategorie(autocompleteRecherche:AutocompleteRecherche) {
    const currentAutoComplete = this._critereRecherchesCategorie$.getValue();
    const filtre=currentAutoComplete.find((res)=>res.id==autocompleteRecherche.id)
    const index=currentAutoComplete.indexOf(filtre)
    if(index!=-1){
      currentAutoComplete.splice(index,1)
      this._critereRecherchesCategorie$.next(currentAutoComplete);
    }
  }
  setAutoCompleteRecherchesCategorie(autocompleteRecherches:AutocompleteRecherche[]){
    return this._autoCompleteRecherchesCategorie$.next(autocompleteRecherches)
  }
  get autoCompleteRecherchesCategorie$(){
    return this._autoCompleteRecherchesCategorie$.asObservable()
  }
}
