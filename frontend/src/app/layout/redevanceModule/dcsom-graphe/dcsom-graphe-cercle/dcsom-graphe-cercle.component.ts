import {Component, Input, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {HomeCard} from "../../../../core/interfaces/infotuiles/homeCard";

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
  constructor() {
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
