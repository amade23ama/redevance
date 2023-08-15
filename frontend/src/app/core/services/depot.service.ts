import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Exploitation} from "../interfaces/exploitation";
import {BehaviorSubject, catchError, tap, throwError} from "rxjs";
import {NotificationService} from "./notification.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {
  DepotValidationColumnPopupComponent
} from "../../layout/depot-module/depot-validation-column-popup/depot-validation-column-popup.component";
import {FileInfo} from "../interfaces/file.info";
import {Site} from "../interfaces/site";
import {Depot} from "../interfaces/infotuiles/depot";

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
  constructor(private http:HttpClient,private notification: NotificationService,
              public dialog: MatDialog,) {
  }
  creerDepot(formData: FormData){
    this.setFichierCourant(formData)
    return this.http.post<any>(this.url + '/fileHeader', formData).pipe(
      tap((res:FileInfo)=> {
        console.log("depot en cours de creation ", res);
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
      return this.http.post<any>(this.url + '/upload',formData).pipe(
        tap((res)=> {
          console.log("confirmation depot ",res);
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
          this.setDepots(res);
        }),
        catchError((err) => {
          this.notification.error("erreur de chargement des depots")
          return throwError(() => err)
        })
      )
  }
}
