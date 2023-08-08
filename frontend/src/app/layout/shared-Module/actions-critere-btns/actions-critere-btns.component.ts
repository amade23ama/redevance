import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {FormControl} from "@angular/forms";
import {Filtre} from "../../../core/interfaces/filtre";

@Component({
  selector: 'app-actions-critere-btns',
  templateUrl: './actions-critere-btns.component.html',
  styleUrls: ['./actions-critere-btns.component.scss']
})
export class ActionsCritereBtnsComponent implements OnInit{
  @Input() search:FormControl;
  @Output() btnAjouter = new EventEmitter();
  @Input() label:string;
  @Input() tooltip:string;
  @Input() numberResult:number
  @Input()singulier:string
  @Input()plurier:string

  constructor() {
  }
  ngOnInit() {
  }
  ajouter(){
    this.btnAjouter.emit(true)
  }

}
