import {Component, OnInit} from '@angular/core';
import {AppConfigService} from "../../core-module/services/app-config.service";

@Component({
  selector: 'app-dscom-header',
  templateUrl: './dscom-header.component.html',
  styleUrls: ['./dscom-header.component.css']
})
export class DscomHeaderComponent implements OnInit  {
  constructor(public appConfig:AppConfigService) {
  }
  ngOnInit(): void {
  }
}
