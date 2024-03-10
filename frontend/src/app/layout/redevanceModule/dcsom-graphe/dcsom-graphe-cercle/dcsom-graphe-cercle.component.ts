import {Component, Input, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {HomeCard} from "../../../../core/interfaces/infotuiles/homeCard";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {Color, ScaleType} from "@swimlane/ngx-charts";

@Component({
  selector: 'app-dcsom-graphe-cercle',
  templateUrl: './dcsom-graphe-cercle.component.html',
  styleUrls: ['./dcsom-graphe-cercle.component.scss']
})
export class DcsomGrapheCercleComponent implements OnInit{
  @Input() homeCard:Observable<HomeCard>
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;
  legendPosition: string = 'below';
  isLtMd: boolean;
  colorScheme: Color = {
    name: 'myScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#DC7633', '#F39C12', '#EDBB99', '#F39C12', '#FAD7A0', '#F5B7B1'],
  };
  constructor(private breakpointObserver: BreakpointObserver) {
    this.breakpointObserver
      .observe([Breakpoints.Handset, Breakpoints.Small])
      .subscribe((result) => {
        this.isLtMd = !result.matches;
      });
  }

  ngOnInit() {
  }
  onSelect(data:any): void {
  }

  onActivate(data:any): void {
  }

  onDeactivate(data:any): void {
  }
}
