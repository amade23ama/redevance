import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActionBtn} from "../../../core/interfaces/actionBtn";

@Component({
  selector: 'app-action-btns',
  templateUrl: './action-btns.component.html',
  styleUrls: ['./action-btns.component.scss']
})
export class ActionBtnsComponent implements OnInit{
  @Input() btns: ActionBtn[];
  @Output() btnClick = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  onClick(id: string) {

    this.btnClick.emit(id);

  }
}
