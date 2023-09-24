import { NgIf } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

export interface DialogData {
  id: number;
  name: string;
}

@Component({
  selector: 'suppression',
  templateUrl: './suppression.component.html',
  styleUrls: ['./suppression.component.scss'],
  standalone: true,
  imports: [MatDialogModule, NgIf, MatIconModule],
})
export class SuppressionComponent {

  constructor(
    public dialogRef: MatDialogRef<SuppressionComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
