import {Component, OnInit} from '@angular/core';
import {AppConfigService} from "../../../core/services/app-config.service";

@Component({
  selector: 'app-dscom-footer',
  templateUrl: './dscom-footer.component.html',
  styleUrls: ['./dscom-footer.component.scss']
})
export class DscomFooterComponent implements OnInit  {
  constructor(public appConfig:AppConfigService) {
  }
  ngOnInit(): void {
  }

}
