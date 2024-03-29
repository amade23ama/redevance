import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
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
import {FileValidators} from "ngx-file-drag-drop";

@Component({
  selector: 'recherche-vehicule',
  templateUrl: './recherche-vehicule.component.html',
  styleUrls: ['./recherche-vehicule.component.scss']
})
export class RechercheVehiculeComponent implements OnInit{
  disableBtnSupprimer:boolean=true
  search:FormControl =new FormControl('');
  /** la liste des véhicules */
  listVehicule: MatTableDataSource<Vehicule>;

  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  nb$=this.vehiculeService.nbVehicules$;
  // nombre de ligne par page
  //pageSizeOptions: number[] = [10, 20, 30];
  pageSize = 10; // nb ligne par page par défaut
  itemSize:number=0;
  totalItems = 10;
  page = 0;
  size = 10;
  itemsPerPage = 10;
  newPage=0
  croll:boolean=false;
  private lastScrollIndex = 0;
  // les noms des colones
  displayedColumns: string[] = ['id','immatriculation', 'type', 'volume','poidsVide','dateCreation','dateModification','actions'];
  vehicules$=this.vehiculeService.vehicules$
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesVehicule$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesVehicule$
  file: FormControl = new FormControl('', [FileValidators.required, FileValidators.maxFileCount(1)])
  myform: FormGroup = this.builder.group({
    file:this.file
  })
  typeFile=".txt,.csv"
  /** constructor */
  constructor(public appConfig: AppConfigService, public vehiculeService: VehiculeService,private builder: FormBuilder,
              private router:Router,private autocompleteRechercheService:AutocompleteRechercheService) {}


  ngOnInit(): void {
    this.vehiculeService.setVehicules([]);
    this.rechargementVehicule()
    /** appel du service rechercherVehicules pour recupérer toutes les véhicules en base */
    this.vehiculeService.vehicules$.subscribe((data) =>{
    //alimentation du tableau
    this.listVehicule = new MatTableDataSource<Vehicule>(data);
    //this.listVehicule.paginator=this.paginator;
    this.listVehicule.sort=this.sort;
    this.itemSize=data.length
    this.totalItems = 100;
      this.listVehicule.sortingDataAccessor = (item, property) => {
        return this.getPropertyValue(item, property);
      };
    });
    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        this.page=0
        this.newPage=0;
        this.croll=false;
        return this.autocompleteRechercheService.autocompleteVehicule(capture);
      })
    ).subscribe();

  }
  private getPropertyValue(item: any, property: string): any {
    switch(property) {
      case 'type': return item.categorie.type;
      case 'volume': return item.categorie.volume;
      default: return item[property];
    }
  }
  redirect(vehicule: Vehicule) {
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
          page :this.newPage,
          size :this.size,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.vehiculeService.chargementVehiculeParCritere(critereRecherche, this.croll).subscribe()
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
      this.newPage=newIndex;
      this.croll=true
      this.rechargementVehicule();
    }
  }

  getItemSize() {
    return 50;
  }
  onValueChange(files: File[]) {
    const file=files[0]
    const formData = new FormData();
    formData.append('file', file);
  }
  valider(){
    const file: File = this.myform.value.file[0];
    const formData: FormData = new FormData();
    formData.append('file', file);
    this.vehiculeService.creerDepot(formData).subscribe()
    this.file.setValue(null);
  }
}
