import { Component } from '@angular/core';
import {SpinnerService} from "../../core-module/services/spinner.service";

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss']
})
export class SpinnerComponent {
  spinnerActive = true;

  constructor(
    public spinnerHandler: SpinnerService
  ) {
    this.spinnerHandler.showSpinner.subscribe(this.showSpinner.bind(this));
  }

  showSpinner = (state: boolean): void => {
    this.spinnerActive = state;
  }
}
