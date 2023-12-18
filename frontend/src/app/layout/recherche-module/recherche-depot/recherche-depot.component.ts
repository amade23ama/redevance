import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from "@angular/forms";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { debounceTime, distinctUntilChanged, switchMap } from "rxjs";
import { CritereRecherche } from "src/app/core/interfaces/critere.recherche";
import { BuilderDtoJsonAbstract } from "../../../core/interfaces/BuilderDtoJsonAbstract";
import { AutocompleteRecherche } from "../../../core/interfaces/autocomplete.recherche";
import { Depot } from "../../../core/interfaces/depot";
import { AppConfigService } from "../../../core/services/app-config.service";
import { AutocompleteRechercheService } from "../../../core/services/autocomplete.recherche.service";
import { DepotService } from "../../../core/services/depot.service";
import {ReferenceService} from "../../../core/services/reference.service";

@Component({
  selector: 'app-recherche-depot',
  templateUrl: './recherche-depot.component.html',
  styleUrls: ['./recherche-depot.component.scss']
})
export class RechercheDepotComponent implements OnInit{
  search:FormControl =new FormControl('');
  displayedColumns: string[] = ['id', 'nom','station', 'dateHeureDepot','dateHeureFinDepot','statut','deposeur'
  ,'nbChargementTotal','nbChargementDeposes','nbChargementReDeposes','nbChargementErreur'];
  depots$=this.depotService.depots$;
  listDepots: MatTableDataSource<Depot>;
  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  itemSize:number=0;
  nb$=this.depotService.nbDepots$
  pageSize = 10; // nb ligne par page par défaut
  totalItems = 10;
  page = 0;
  size = 10;
  itemsPerPage = 10;
  newPage=0
  croll:boolean=false;
  private lastScrollIndex = 0;

  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesDepot$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesDepot$
  constructor(public appConfig: AppConfigService,private router:Router,public depotService:DepotService,
              private autocompleteRechercheService:AutocompleteRechercheService,
              private referenceService:ReferenceService) {
  }
  ouvreNouveauDepot(){
    this.router.navigate(['depot/creer'])
  }

  ngOnInit(): void {
    this.referenceService.getAllAnnee().subscribe()
    this.depotService.setDepots([]);
    this.rechargementDepot();
    //this.depotService.getAllDepots().subscribe();

    this.depotService.depots$.subscribe((depots) => {
      //alimentation du tableau
      this.listDepots = new MatTableDataSource<Depot>(depots);
      //this.listDepots.paginator=this.paginator;
      //this.sort.sort(({ id: 'dateHeureDepot', start: 'desc'}) as MatSortable);
      this.listDepots.sort=this.sort;
      this.itemSize=depots.length
      this.totalItems = 100;
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
    this.router.navigate(['recherche/depotChargement'], {queryParams: {'contextInfo':depot.id }});
  }

  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRechercheDepot(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRechercheDepot(autocompleteRecherche)
  }

  rechargementDepot(){
    this.critereRecherches$.subscribe((res)=>{
      if(res) {
        const critereRecherche:CritereRecherche   = {
          autocompleteRecherches:res,
          page :this.newPage,
          size :this.size,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.depotService.chargementDepotsParCritere(critereRecherche,this.croll).subscribe((res)=>{

        })
      }
    })
  }

  onScrollEnd(index: number) {
    const isScrollingDown = index > this.lastScrollIndex;
    this.lastScrollIndex = index;
    if (isScrollingDown) {
      this.page++
      const totalLoadedItems = this.page * this.itemsPerPage;
      const newIndex = Math.floor(totalLoadedItems / this.itemsPerPage)
      this.newPage = newIndex;
      this.croll = true
      this.rechargementDepot();
    }
  }

  getItemSize() {
    return 50;
  }
}
