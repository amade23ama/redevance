import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from "@angular/forms";
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from "@angular/router";
import { debounceTime, distinctUntilChanged, switchMap } from "rxjs";
import { Vehicule } from 'src/app/core/interfaces/vehicule';
import { VehiculeService } from 'src/app/core/services/vehicule.service';
import { BuilderDtoJsonAbstract } from "../../../core/interfaces/BuilderDtoJsonAbstract";
import { AutocompleteRecherche } from "../../../core/interfaces/autocomplete.recherche";
import { CritereRecherche } from "../../../core/interfaces/critere.recherche";
import { AppConfigService } from "../../../core/services/app-config.service";
import { AutocompleteRechercheService } from "../../../core/services/autocomplete.recherche.service";

@Component({
  selector: 'recherche-vehicule',
  templateUrl: './recherche-vehicule.component.html',
  styleUrls: ['./recherche-vehicule.component.scss']
})
export class RechercheVehiculeComponent implements OnInit{
  search:FormControl =new FormControl('');
  /** la liste des véhicules */
  listVehicule: MatTableDataSource<Vehicule>;

  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // nombre de ligne par page
  pageSizeOptions: number[] = [5, 10, 20];
  pageSize = 5; // nb ligne par page par défaut

  // les noms des colones
  displayedColumns: string[] = ['NomRS', 'Téléphone',/* 'Email',*/ 'Immatriculation', 'Classe', 'Volume','dateCreation','actions'];
  vehicules$=this.vehiculeService.vehicules$
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesVehicule$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesVehicule$
  /** constructor */
  constructor(public appConfig: AppConfigService, public vehiculeService: VehiculeService,
              private router:Router,private autocompleteRechercheService:AutocompleteRechercheService) {}


  ngOnInit(): void {
    this.rechargementVehicule()
    /** appel du service rechercherVehicules pour recupérer toutes les véhicules en base */
    this.vehiculeService.vehicules$.subscribe((data) =>{
    //alimentation du tableau
    this.listVehicule = new MatTableDataSource<Vehicule>(data);
    this.listVehicule.paginator=this.paginator;
    this.listVehicule.sort=this.sort;
    });
    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        return this.autocompleteRechercheService.autocompleteVehicule(capture);
      })
    ).subscribe();

  }

  redirect(vehicule: Vehicule) {
    console.log("ertyhjk", vehicule);
  }
  chargervehicule(vehicule: Vehicule){
    this.router.navigate(['admin/vehicule'], {queryParams: {'contextInfo':vehicule.id }});
  }
  ouvreNouveauVehicule(){
    this.router.navigate(['admin/vehicule'])
  }
  initial(vehicule: Vehicule){
    return vehicule.immatriculation.charAt(0).toUpperCase()
  }
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }
  chargerVoiture(vehicule: Vehicule){
    this.router.navigate(['admin/vehicule'], {queryParams: {'contextInfo':vehicule.id }});
  }
  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRechercheVehicule(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRechercheVehicule(autocompleteRecherche)
  }
  rechargementVehicule(){
    this.critereRecherches$.subscribe((res)=>{
      if(res) {
        const critereRecherche   = {
          autocompleteRecherches:res,
          page :1,
          size :20,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.vehiculeService.chargementVehiculeParCritere(critereRecherche).subscribe()
      }
    })
  }
}
