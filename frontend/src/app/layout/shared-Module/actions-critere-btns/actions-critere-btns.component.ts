import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {FormControl} from "@angular/forms";
import {Filtre} from "../../../core/interfaces/filtre";

@Component({
  selector: 'app-actions-critere-btns',
  templateUrl: './actions-critere-btns.component.html',
  styleUrls: ['./actions-critere-btns.component.scss']
})
export class ActionsCritereBtnsComponent implements OnInit{
  @Input() reach:FormControl;
  @Output() btnAjouter = new EventEmitter();
  @Input() label:string;
  @Input() filtres:Filtre[]
  @Output() supprimer = new EventEmitter();
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;

  constructor() {
  }
  ngOnInit() {
  }
  onClick(){
    this.btnAjouter.emit(true)
  }

  remove(filtre:Filtre): void {
    this.supprimer.emit(filtre)
  }
}
