import {AfterViewInit, Component, ElementRef, OnInit, QueryList, ViewChild, ViewChildren} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ParamService} from "../../../core/services/param.service";
import {AppConfigService} from "../../../core/services/app-config.service";
import {debounceTime, distinctUntilChanged, map, Observable, of, startWith, tap} from "rxjs";
import {animate, group, state, style, transition, trigger} from "@angular/animations";
import {openCloseTransition} from "../../../core/interfaces/open-close.transition";
import {MatRadioButton, MatRadioChange} from "@angular/material/radio";

class Bank {
  name:string
}

@Component({
  selector: 'app-site',
  templateUrl: './site.component.html',
  styleUrls: ['./site.component.scss'],
  animations: [openCloseTransition]
})
export class SiteComponent implements OnInit,AfterViewInit {
  show = false;
  id: FormControl = new FormControl()
  nom: FormControl = new FormControl()
  localite: FormControl = new FormControl();
  dateCreation: FormControl = new FormControl();
  dateModification:FormControl = new FormControl();
  myform: FormGroup = this.builder.group({
    id: this. id,
    nom: this.nom,
    localite: this.localite,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification,
  })
  radioOptions = [
    { label: 'Option 1', value: true },
    { label: 'Option 2', value: true },
    { label: 'Option 3', value: true }
  ];


  constructor(private builder: FormBuilder,
              public appConfig:AppConfigService,private paramService: ParamService) {
  }
  ngOnInit(): void {
    console.error(" log")
  }

  @ViewChildren(MatRadioButton) radioButtons!: QueryList<MatRadioButton>;

  ngAfterViewInit() {
    //this.setupRadioChangeHandler();
  }

  /*setupRadioChangeHandler() {
    this.radioButtons.forEach((radioButton) => {
      radioButton.change.subscribe((event) => {
        this.handleRadioChange(event);
      });
    });
  }*/

  handleRadioChange(event: MatRadioChange) {
    const selectedValue = event.value;
    const parentElement = event.source._elementRef.nativeElement.closest('.row');
    const highlightElements = document.querySelectorAll('.highlight');
    highlightElements.forEach((element) => {
      element.classList.remove('highlight');
    });
    parentElement.classList.add('highlight');
  }

}
























