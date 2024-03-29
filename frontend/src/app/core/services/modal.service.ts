import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/app/layout/shared-Module/dialog/confirmation-dialog/confirmation-dialog.component';
import { SuppressionComponent } from 'src/app/layout/shared-Module/dialog/suppression/suppression.component';
import { AnnulationModaleComponent } from '../../layout/shared-Module/dialog/annulation-modale/annulation-modale.component';

@Injectable({
  providedIn: 'root'
})
/**
 * Modal Service
 */
export class ModalService {

  /** constructor */
  constructor(public dialog: MatDialog) { }

  /**
   * ouvrir Modale Annulation
   * @param enterAnimationDuration  enterAnimationDuration
   * @param exitAnimationDuration  exitAnimationDuration
   * @param action action
   */
  ouvrirModaleAnnulation(previousUrl: string, action: string): void {
    this.dialog.open(AnnulationModaleComponent, {
      width: '600px',
      position: {top:'200px'},
      data: {url: previousUrl, action: action},
    });
  }

  /**
   * Modal de confirmation
   * @param info
   */
  ouvrirModalConfirmation(info: string) {
    this.dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      position: {top:'200px'},
      data: {
        information: info,
      },
    });
  }


  ouvrirSuppressionModal(name: String, id: number){
    const dialogRef = this.dialog.open(SuppressionComponent, {
      data: {name: name, id: id},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed', result);
    });
  }

}
