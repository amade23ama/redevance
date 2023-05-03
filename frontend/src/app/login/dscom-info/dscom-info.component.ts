import {Component, OnInit} from '@angular/core';
import {AppConfigService} from "../../core/services/app-config.service";

@Component({
  selector: 'app-dscom-info',
  templateUrl: './dscom-info.component.html',
  styleUrls: ['./dscom-info.component.scss']
})
export class DscomInfoComponent implements OnInit {
  constructor(public appConfig: AppConfigService) {
  }

  ngOnInit(): void {
  }
}
