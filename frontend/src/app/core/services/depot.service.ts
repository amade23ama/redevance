import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { BehaviorSubject, catchError, tap, throwError } from "rxjs";
import { Globals } from "src/app/app.constants";
import { environment } from "../../../environments/environment";
import {
  DepotValidationColumnPopupComponent
} from "../../layout/depot-module/depot-validation-column-popup/depot-validation-column-popup.component";
import { CritereRecherche } from "../interfaces/critere.recherche";
import { Depot } from "../interfaces/depot";
import { FileInfo } from "../interfaces/file.info";
import { Page } from "../interfaces/page";
import { NotificationService } from "./notification.service";

@Injectable({
  providedIn:"root"
})
export class DepotService{
  /** url de base des webservices produit */
  private url = environment.apiUrl + '/v1/depot';
  /** Le popup de la validation du contrat selon le context. */
  confirmDialog: any;
  fileInfoCourant: FileInfo = new FileInfo();
  fichierCourant: FormData =new FormData();
  private _numeroDepot$: BehaviorSubject<number> = new BehaviorSubject(<number>(null));
  private _depots: BehaviorSubject<Depot[]> = new BehaviorSubject<Depot[]>( []);
  private _depot: BehaviorSubject<Depot> = new BehaviorSubject<Depot>( {}as null);
  private _nbDepots: BehaviorSubject<number> = new BehaviorSubject<number>(null);
  constructor(private http:HttpClient,private notification: NotificationService,
              public dialog: MatDialog, private globals: Globals) {
  }
  creerDepot(formData: FormData){
    this.setFichierCourant(formData)
    return this.http.post<any>(this.url + '/fileHeader', formData).pipe(
      tap((res:FileInfo)=> {
        this.notification.success("depot en cours de creation")
        this.setFileInfoCourant(res)
      }),
      catchError((err) => {
        this.notification.error("erreur de creation de depot")
        return throwError(() => err)
      })
    );
  }
  deposerFichier(formData: FormData){
      return this.http.post<any>(this.url + '/Batch/upload',formData).pipe(
        tap((res)=> {
          this.notification.success("confirmation depot")
          this.setNumeroDepot(res as number)
        }),
        catchError((err) => {
          this.notification.error("erreur de confirmation depot")
          return throwError(() => err)
        })
      );
  }
  ouvreValidationColumnPopUpDepot(contextValidation: string) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.width = '80%';
    dialogConfig.data = {
      contextValidation: contextValidation,
      fileInfo: this.getFileInfoCourant(),
      file:this.getFichierCourant()
    };
    this.confirmDialog = this.dialog.open(DepotValidationColumnPopupComponent, dialogConfig);
  }
  getFileInfoCourant() {
    return this.fileInfoCourant;
  }


  setFileInfoCourant(fileInfoCourant: FileInfo) {
    this.fileInfoCourant = fileInfoCourant;
  }

  getFichierCourant() {
    return this.fichierCourant;
  }


  setFichierCourant(fichierCourant: FormData) {
    this.fichierCourant = fichierCourant;
  }
 setNumeroDepot(num:number){
    this._numeroDepot$.next(num);
 }
 get numeroDepot$(){
    return this._numeroDepot$.asObservable()
 }
   setDepots(depots:Depot[]){
    this._depots.next(depots)
  }
  get depots$(){
    return this._depots.asObservable()
  }

  getAllDepots(){
    return this.http.get<Depot[]>(this.url + '/rechercher')
      .pipe(
        tap((res:Depot[])=> {
          //this.setDepots(res.map((result)=>Depot.fromJson(result,Depot)));
          this.setNbDepots(res.length)
          this.setDepots(res);
        }),
        catchError((err) => {
          this.notification.error("erreur de chargement des depots")
          return throwError(() => err)
        })
      )
  }
  getDepotParId(id: number) {
    return this.http.get<Depot>(this.url+`/rechercherById/${id}`)
      .pipe(
        tap((depot:Depot) => {
          this.setDepot(Depot.fromJson(depot,Depot))
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation depot ")
          return throwError(() => err)
        })
      )
  }
  setDepot(depot:Depot){
    this._depot.next(depot)
  }
  get depot$(){
    return this._depot.asObservable()
  }
  chargementDepotParCritere(critereRecherche:CritereRecherche ) {
    return this.http.post<Depot[]>(this.url+"/recherche",critereRecherche)
      .pipe(
        tap((res:Depot[]) => {
          this.setNbDepots(res.length)
          this.setDepots(res);
        }),
        catchError((err) => {
          this.notification.error(" erreurr de recuperation Utilisateur ")
          return throwError(() => err)
        })
      )
  }

  chargementDepotsParCritere(critereRecherche:CritereRecherche,scroll?: boolean ) {
    this.globals.loading=true
    return this.http.post<Page<Depot>>(this.url+"/rechercheBy",critereRecherche)
      .pipe(
        tap((res:Page<Depot>) => {
          this.setNbDepots(res.totalElements);
          if(res.totalElements==0){
            this.setDepots([])
          }
          if(scroll){
          const result = Array.from(new Set([...this._depots.getValue(),...res.content]));
            this.setDepots(result);
          }else{
            this.setDepots([...res.content]);
          }
          this.globals.loading=false
        }),
        catchError((err) => {
          this.globals.loading=false
          this.notification.error(" erreurr de recuperation Utilisateur ")
          return throwError(() => err)
        })
      )
  }

  get nbDepots$(){
    return this._nbDepots.asObservable()
  }

  /** setExploitations */
  setNbDepots(nombre: number ){
    return this._nbDepots.next(nombre)
  }
}
