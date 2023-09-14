import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {FormControl} from "@angular/forms";
import {Filtre} from "../../../core/interfaces/filtre";
import {AutocompleteRecherche} from "../../../core/interfaces/autocomplete.recherche";
import {Observable, of} from "rxjs";
import {AutocompleteRechercheService} from "../../../core/services/autocomplete.recherche.service";

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
  @Input() recherches:Observable<any>;
  @Input() resultautocompleteRecherche:Observable<AutocompleteRecherche[]>
  @Output() btnAjouterFiltre= new EventEmitter();
  @Output() btnAnnulerFiltre= new EventEmitter();
  @Input() critereRecherches:Observable<AutocompleteRecherche[]>
  selectable: boolean = true;
  removable: boolean = true;
  constructor() {
  }
  ngOnInit() {
    this.recherches = of([
      { id: 1, libelle: 'Item 1', origine: 'Source A' },
      { id: 2, libelle: 'Item 2', origine: 'Source B' },
      { id: 3, libelle: 'Item 3', origine: 'Source C' },
    ])

  }
  ajouter(){
    this.btnAjouter.emit(true)
  }
  removeChip(autocompleteRecherche:AutocompleteRecherche){
    this.btnAnnulerFiltre.emit(autocompleteRecherche)
  }
  change(autocompleteRecherche:AutocompleteRecherche){
    this.btnAjouterFiltre.emit(autocompleteRecherche)
  }
  searchxx(event: any) {
    //this.auttocompleteRechercheService.getAutocompleteMatchesChips(event.query)
     /* .then(data => {
      this.searchSuggestions = data;
    });*/
  }
}
