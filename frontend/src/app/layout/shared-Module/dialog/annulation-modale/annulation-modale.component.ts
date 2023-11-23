import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AppConfigService } from '../../../../core/services/app-config.service';
import {UpperCasePipe} from "@angular/common";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {FlexModule} from "@angular/flex-layout";

// Données propres à la modale
export interface Donnees {
  url: string;
  action: string;
  titre: string;
}

@Component({
  selector: 'annulation-modale',
  templateUrl: './annulation-modale.component.html',
  styleUrls: ['./annulation-modale.component.scss'],
  standalone: true,
  imports: [MatButtonModule, MatDialogModule, UpperCasePipe, MatFormFieldModule, MatIconModule, FlexModule],
})

/**
 * AnnulationModaleComponent
 */
export class AnnulationModaleComponent {

  /** constructor */
  constructor(public appConfig: AppConfigService, public router: Router, public dialogRef: MatDialogRef<AnnulationModaleComponent>, @Inject(MAT_DIALOG_DATA) public data: Donnees) {}

  /**
   * navigation vers la page de recherche du menu d'annulation
   */
  annuler(){
    this.router.navigate([this.data?.url]);
  }

}
