import {Component,Input, OnInit, ViewChild} from "@angular/core";
import {ChartComponent, ChartType} from "ng-apexcharts";
import {Bilan} from "../../../../core/interfaces/infotuiles/bilan";
import {ChartOptions} from "../dcsom-graphe-simple/dcsom-graphe-simple.component";

@Component({
  selector: 'app-dcsom-graphe-bar',
  templateUrl: './dcsom-graphe-bar.component.html',
  styleUrls: ['./dcsom-graphe-bar.component.scss']
})
export class DcsomGrapheBarComponent  implements OnInit {
  @ViewChild("chart") chart: ChartComponent;
  public chartOptions: Partial<ChartOptions>;

  @Input() bilan: Bilan;
  @Input() chartType: ChartType

  constructor() {

  }

  ngOnInit() {

    //this.chartOptions.categories = this.bilan.campagnes.map((x) => x.libelle);
    //this.chartOptions.series[0].data= this.bilan.campagnes.map((x) => x.quantite);
  }


}
