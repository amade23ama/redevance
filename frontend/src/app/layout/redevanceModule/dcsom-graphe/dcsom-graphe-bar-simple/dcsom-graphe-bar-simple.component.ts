import {Component, Input, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {HomeCard} from "../../../../core/interfaces/infotuiles/homeCard";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {Color, ScaleType} from "@swimlane/ngx-charts";

@Component({
  selector: 'app-dcsom-graphe-bar-simple',
  templateUrl: './dcsom-graphe-bar-simple.component.html',
  styleUrls: ['./dcsom-graphe-bar-simple.component.scss']
})
export class DcsomGrapheBarSimpleComponent implements  OnInit{
  @Input() homeCard:Observable<HomeCard>
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
}
