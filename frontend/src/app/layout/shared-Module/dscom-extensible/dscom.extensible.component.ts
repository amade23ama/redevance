import {Component, Input, OnInit} from "@angular/core";
import {AppConfigService} from "../../../core/services/app-config.service";

@Component({
  selector: 'app-dscom-extensible',
  templateUrl: './dscom.extensible.component.html',
  styleUrls: ['./dscom.extensible.component.scss']
})
export class DscomExtensibleComponent implements OnInit{
  @Input() displayEmail:boolean
  @Input() email:string;
  @Input() displayNumDepot:boolean
  @Input() messageDepot:string;
  constructor(public appConfig: AppConfigService) {
  }
  ngOnInit() {
  }
}
