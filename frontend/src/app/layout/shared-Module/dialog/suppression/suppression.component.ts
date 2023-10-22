import {NgIf, UpperCasePipe} from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { AppConfigService } from 'src/app/core/services/app-config.service';
import {FlexModule} from "@angular/flex-layout";

export interface DialogData {
  id: number;
  name: string;
}

@Component({
  selector: 'suppression',
  templateUrl: './suppression.component.html',
  styleUrls: ['./suppression.component.scss'],
  standalone: true,
    imports: [MatButtonModule, MatDialogModule, NgIf, MatIconModule, FlexModule, UpperCasePipe],
})
export class SuppressionComponent {

  constructor(public appConfig: AppConfigService,
    public dialogRef: MatDialogRef<SuppressionComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
