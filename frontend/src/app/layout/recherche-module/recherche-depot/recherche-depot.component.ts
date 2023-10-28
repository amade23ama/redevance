import {Component, OnInit, ViewChild} from '@angular/core';
import {AppConfigService} from "../../../core/services/app-config.service";
import {ExploitationService} from "../../../core/services/exploitation.service";
import {Router} from "@angular/router";
import {FormControl} from "@angular/forms";
import {DepotService} from "../../../core/services/depot.service";
import {MatTableDataSource} from "@angular/material/table";
import {Site} from "../../../core/interfaces/site";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Depot} from "../../../core/interfaces/depot";
import {DatePipe} from "@angular/common";
import {BuilderDtoJsonAbstract} from "../../../core/interfaces/BuilderDtoJsonAbstract";
import {DepotChargementComponent} from "../../depot-module/depot-chargement/depot-chargement.component";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {AutocompleteRechercheService} from "../../../core/services/autocomplete.recherche.service";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs";
import {AutocompleteRecherche} from "../../../core/interfaces/autocomplete.recherche";

@Component({
  selector: 'app-recherche-depot',
  templateUrl: './recherche-depot.component.html',
  styleUrls: ['./recherche-depot.component.scss']
})
export class RechercheDepotComponent implements OnInit{
  search:FormControl =new FormControl('');
  displayedColumns: string[] = ['id', 'nom','station', 'dateHeureDepot','dateHeureFinDepot','statut','deposeur','nbChargementErreur'
  ,'nbChargementDeposes'];
  depots$=this.depotService.depots$;
  listDepots: MatTableDataSource<Depot>;
  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // nombre de ligne par page
  pageSizeOptions: number[] = [10, 20, 30];
  pageSize = 10; // nb ligne par page par défaut
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesDepot$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesDepot$
  constructor(public appConfig: AppConfigService,private router:Router,public depotService:DepotService,
              private autocompleteRechercheService:AutocompleteRechercheService) {
  }
  ouvreNouveauDepot(){
    this.router.navigate(['depot/creer'])
  }

  ngOnInit(): void {
    this.depotService.getAllDepots().subscribe()
    this.depotService.depots$.subscribe((depots) => {
      console.log("les sites: ", depots);
      //alimentation du tableau
      this.listDepots = new MatTableDataSource<Depot>(depots);
      this.listDepots.paginator=this.paginator;
      this.listDepots.sort=this.sort;
    })
    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        return this.autocompleteRechercheService.autocompleteDepot(capture);
      })
    ).subscribe();
  }
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }
  chargerDepot(depot:Depot){
    console.log("vvv")
    this.router.navigate(['recherche/depotChargement'], {queryParams: {'contextInfo':depot.id }});
  }

  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRechercheDepot(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRechercheDepot(autocompleteRecherche)
  }
}
