import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormControl } from "@angular/forms";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { Observable, debounceTime, distinctUntilChanged, switchMap } from "rxjs";
import { BuilderDtoJsonAbstract } from "../../../core/interfaces/BuilderDtoJsonAbstract";
import { AutocompleteRecherche } from "../../../core/interfaces/autocomplete.recherche";
import { Chargement } from "../../../core/interfaces/chargement";
import { CritereRecherche } from "../../../core/interfaces/critere.recherche";
import { AppConfigService } from "../../../core/services/app-config.service";
import { AutocompleteRechercheService } from "../../../core/services/autocomplete.recherche.service";
import { ChargementService } from "../../../core/services/chargement.service";

@Component({
  selector: 'app-recherche-chargement',
  templateUrl: './recherche-chargement.component.html',
  styleUrls: ['./recherche-chargement.component.scss']
})
export  class RechercheChargementComponent implements  OnInit{
  chargements$=this.chargementService.chargements$
  search:FormControl =new FormControl('');
  searchDate:FormControl =new FormControl('');
  /** la liste des véhicules */
  listChargements: MatTableDataSource<Chargement>;
  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // nombre de ligne par page
  pageSizeOptions: number[] = [10, 20, 30];
  pageSize = 10; // nb ligne par page par défaut
  rechercheChargements: Chargement[] = [];
  // les noms des colones  'Date Modification',,'categorie'
  displayedColumns: string[] =['datePesage', 'site','produit','exploitation', 'destination','vehicule','transporteur'
  ,'poids','poidsSubst','volumeSubst','ecart','actions'];
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesChargement$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesChargement$
  constructor(public appConfig: AppConfigService,public chargementService:ChargementService,
              private router:Router, private autocompleteRechercheService:AutocompleteRechercheService) {
  }
  ngOnInit() {

   //this.chargementService.getAllChargements().subscribe()

    this.chargementService.chargements$.subscribe((chargements) => {
      if(chargements!==null){
        console.log("les chargement: ", chargements);
        //alimentation du tableau
        this.rechercheChargements=chargements
        this.listChargements = new MatTableDataSource<Chargement>(this.rechercheChargements);
        this.listChargements.paginator=this.paginator;
        this.listChargements.sort=this.sort;
      }
      })
    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        return this.autocompleteRechercheService.autocompleteChargement(capture);
      })
    ).subscribe();
    this.rechargementChargement();
  }

  ouvreNouveauChargement(){
    this.router.navigate(['depot/creer'])
  }

  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }

  chargerChargement(chargement: Chargement){
    this.router.navigate(['admin/chargement'], {queryParams: {'contextInfo': chargement.id }});
  }

  export(critereRecherches:Observable<AutocompleteRecherche[]>){
    const critereRecherche:CritereRecherche=new CritereRecherche()
    critereRecherches.subscribe((res)=>{
      critereRecherche.autocompleteRecherches=res;
    })
    critereRecherche.annee=this.searchDate.value
   this.chargementService.exportDocumentChargementParCritere(critereRecherche).subscribe()
  }
  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRechercheChargement(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRechercheChargement(autocompleteRecherche)
  }
  rechargementChargement(){
    this.critereRecherches$.subscribe((res)=>{
      if(res) {
        const critereRecherche   = {
          autocompleteRecherches:res,
          page :1,
          size :20,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.searchDate.valueChanges.subscribe((value)=>{
          critereRecherche.annee=this.searchDate.value;
          this.chargementService.chargementChargementParCritere(critereRecherche).subscribe()
        })
        critereRecherche.annee=this.searchDate.value;
        this.chargementService.chargementChargementParCritere(critereRecherche).subscribe()
      }

    })
  }
}
