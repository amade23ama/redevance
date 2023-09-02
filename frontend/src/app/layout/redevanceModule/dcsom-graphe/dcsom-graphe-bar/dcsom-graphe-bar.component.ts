import {Component, Input, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {HomeCard} from "../../../../core/interfaces/infotuiles/homeCard";


@Component({
  selector: 'app-dcsom-graphe-bar',
  templateUrl: './dcsom-graphe-bar.component.html',
  styleUrls: ['./dcsom-graphe-bar.component.scss']
})
export class DcsomGrapheBarComponent  implements  OnInit{
  @Input() homeCard:Observable<HomeCard>
  constructor() {
  }
  ngOnInit() {
  }

}
