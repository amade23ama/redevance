import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AnnulationModaleComponent } from '../modals/annulation-modale/annulation-modale.component';

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
}
