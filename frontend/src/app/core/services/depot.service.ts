import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Exploitation} from "../interfaces/exploitation";
import {catchError, tap, throwError} from "rxjs";
import {NotificationService} from "./notification.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {
  DepotValidationColumnPopupComponent
} from "../../layout/depot-module/depot-validation-column-popup/depot-validation-column-popup.component";

@Injectable({
  providedIn:"root"
})
export class DepotService{
  /** url de base des webservices produit */
  private url = environment.apiUrl + '/v1/depot';
  /** Le popup de la validation du contrat selon le context. */
  confirmDialog: any;
  constructor(private http:HttpClient,private notification: NotificationService,
              public dialog: MatDialog,) {
  }
  creerDepot(formData: FormData){
    return this.http.post<any>(this.url + '/creer', formData).pipe(
      tap((res:any)=> {
        console.log("depot en cours de creation ", res);
        this.notification.success("depot en cours de creation")
      }),
      catchError((err) => {
        this.notification.error("erreur de creation de depot")
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
      //contrat: this.getContratCourant()
    };
    this.confirmDialog = this.dialog.open(DepotValidationColumnPopupComponent, dialogConfig);
  }
}
