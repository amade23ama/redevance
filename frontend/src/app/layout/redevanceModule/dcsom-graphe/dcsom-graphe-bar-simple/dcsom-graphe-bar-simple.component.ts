import {Component, Input, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {HomeCard} from "../../../../core/interfaces/infotuiles/homeCard";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";

@Component({
  selector: 'app-dcsom-graphe-bar-simple',
  templateUrl: './dcsom-graphe-bar-simple.component.html',
  styleUrls: ['./dcsom-graphe-bar-simple.component.scss']
})
export class DcsomGrapheBarSimpleComponent implements  OnInit{
  @Input() homeCard:Observable<HomeCard>
  isLtMd: boolean;
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
