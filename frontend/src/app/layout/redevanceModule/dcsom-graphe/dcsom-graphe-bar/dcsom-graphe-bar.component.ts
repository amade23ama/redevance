import {Component, Input, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {HomeCard} from "../../../../core/interfaces/infotuiles/homeCard";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";


@Component({
  selector: 'app-dcsom-graphe-bar',
  templateUrl: './dcsom-graphe-bar.component.html',
  styleUrls: ['./dcsom-graphe-bar.component.scss']
})
export class DcsomGrapheBarComponent  implements  OnInit{
  @Input() homeCard:Observable<HomeCard>
  isLtMd: boolean;

  constructor(private breakpointObserver: BreakpointObserver) {

  }
  ngOnInit() {
    this.breakpointObserver
      .observe([Breakpoints.Handset, Breakpoints.Small])
      .subscribe((result) => {
        this.isLtMd = !result.matches;
      });
  }

}
