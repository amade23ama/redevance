import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

// Données propres à la modale
export interface Donnees {
  url: string;
}

@Component({
  selector: 'annulation-modale',
  templateUrl: './annulation-modale.component.html',
  styleUrls: ['./annulation-modale.component.scss'],
  standalone: true,
  imports: [MatButtonModule, MatDialogModule],
})
 
/**
 * AnnulationModaleComponent
 */
export class AnnulationModaleComponent {

  /** constructor */
  constructor(public router: Router, public dialogRef: MatDialogRef<AnnulationModaleComponent>, @Inject(MAT_DIALOG_DATA) public data: Donnees) {}

  /**
   * navigation vers la page de recherche du menu d'annulation
   */
  annuler(){
    this.router.navigate([this.data.url]);
  }

}
