import {NgIf, UpperCasePipe} from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { AppConfigService } from 'src/app/core/services/app-config.service';
import {FlexModule} from "@angular/flex-layout";

export interface DialogData {
  title: string;
  question: string;
}

@Component({
  selector: 'app-dialogue',
  templateUrl: './dialogue.component.html',
  styleUrls: ['./dialogue.component.scss'],
  standalone: true,
  imports: [MatButtonModule, MatDialogModule, NgIf, MatIconModule, FlexModule, UpperCasePipe],
})
export class DialogueComponent {

  constructor(public appConfig: AppConfigService,
    public dialogRef: MatDialogRef<DialogueComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
