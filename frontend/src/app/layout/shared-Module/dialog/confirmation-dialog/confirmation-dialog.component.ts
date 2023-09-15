import { NgIf } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

export interface DialogData {
  information : string;
}

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css'],
  standalone: true,
  imports: [MatDialogModule, NgIf, MatIconModule],
})
export class ConfirmationDialogComponent implements OnInit  {

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData) {}
  
  ngOnInit(): void {
  }
}
