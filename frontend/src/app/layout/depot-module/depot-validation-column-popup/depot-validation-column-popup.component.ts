import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AppConfigService} from "../../../core/services/app-config.service";
import {DepotService} from "../../../core/services/depot.service";

@Component({
  selector: 'app-depot-validation-column-popup',
  templateUrl: './depot-validation-column-popup.component.html',
  styleUrls: ['./depot-validation-column-popup.component.scss']
})
export class DepotValidationColumnPopupComponent implements OnInit{
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public appConfig: AppConfigService,
              public dialogRef: MatDialogRef<DepotValidationColumnPopupComponent>,
              public depotService: DepotService) {
  }
  ngOnInit(): void {
  }

}
