import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild} from "@angular/core";
import {ApexNonAxisChartSeries, ApexResponsive, ApexChart, ChartComponent, ChartType} from "ng-apexcharts";
import {Bilan} from "../../../../core/interfaces/infotuiles/bilan";

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
};


@Component({
  selector: 'app-dcsom-graphe-simple',
  templateUrl: './dcsom-graphe-simple.component.html',
  styleUrls: ['./dcsom-graphe-simple.component.scss']
})
export class DcsomGrapheSimpleComponent  implements OnInit {

  @ViewChild("chart") chart: ChartComponent;
  public chartOptions: Partial<ChartOptions>;
  @Input() bilan:Bilan;
  @Input() chartType: ChartType
  constructor() {

  }
  ngOnInit() {
     this.initGraphe()
    this.chartOptions.labels=this.bilan.campagnes.map((x) => x.libelle);
    this.chartOptions.series=this.bilan.campagnes.map((x) => x.quantite);
  }
 initGraphe(){
   this.chartOptions = {
     series: [],
     chart: {
       type: this.chartType
     },
     labels: [],
     responsive: [
       {
         options: {
           chart: {
           },
           legend: {
             position: "bottom"
           }
         }
       }
     ]
   };
 }
 
}
