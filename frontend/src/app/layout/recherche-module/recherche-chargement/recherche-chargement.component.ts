import { SelectionModel } from "@angular/cdk/collections";
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
import { ReferenceService } from "../../../core/services/reference.service";

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
  selection = new SelectionModel<Chargement>(true, []);
  disableBtnSupprimer:boolean=true;
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
  rechercheChargements: Chargement[] = [];
  // les noms des colones  'Date Modification',,'categorie'
  displayedColumns: string[] =['numImport' ,'datePesage', 'site','produit','exploitation', 'destination','vehicule','transporteur'
  ,'poids','poidsSubst','volumeSubst','ecart','actions'];
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesChargement$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesChargement$
  constructor(public appConfig: AppConfigService,public chargementService:ChargementService,
              private router:Router, private autocompleteRechercheService:AutocompleteRechercheService,
              public readonly  referenceService:ReferenceService) {
  }
  ngOnInit() {
    this.chargementService.setChargements([]);
    this.rechargementChargement();
    this.referenceService.getAnneeMax()
    this.searchDate.setValue(this.referenceService.getAnneeMax())

    this.chargementService.chargements$.subscribe((chargements) => {
      if(chargements!==null){
        this.rechercheChargements=chargements
        this.listChargements = new MatTableDataSource<Chargement>(this.rechercheChargements);
        //this.listChargements.paginator=this.paginator;
        this.listChargements.sort=this.sort;
        this.itemSize=chargements.length
        this.totalItems = 100;
      }
      })
    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        this.page=0
        this.newPage=0;
        this.croll=false;
        return this.autocompleteRechercheService.autocompleteChargement(capture);
      })
    ).subscribe();
    //this.rechargementChargement();
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
          page :this.newPage,
          size :this.size,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.searchDate.valueChanges.subscribe((value)=>{
          critereRecherche.annee=this.searchDate.value;
          this.chargementService.chargementChargementParCritere(critereRecherche, this.croll).subscribe()
        })
        critereRecherche.annee=this.searchDate.value;
        this.chargementService.chargementChargementParCritere(critereRecherche, this.croll).subscribe()
      }

    })
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.listChargements.data.length;
    return numSelected === numRows;
  }
  handleHeaderCheckboxToggle(event: any) {
    if(event.checked){
      this.listChargements.data.forEach(row => this.selection.select(row));
      this.disableBtnSupprimer=false;
    }else {
      this.selection.clear()
      this.disableBtnSupprimer=true;
    }

  }
    checkboxToggle(event: any, chargement: Chargement) {
      if (event.checked) {
        this.selection.select(chargement);
      } else {
        this.selection.deselect(chargement)
      }
      this.disableBtnSupprimer = this.selection.selected.length > 0 ? false : true;
      this.isAllSelected()
    }
    supprimer(critereRecherches: Observable<AutocompleteRecherche[]>) {
      const critereRecherche: CritereRecherche = new CritereRecherche()
      critereRecherches.subscribe((res) => {
        critereRecherche.autocompleteRecherches = res;
      })
      critereRecherche.annee = this.searchDate.value
      if (this.isAllSelected()) {
        this.chargementService.supprimer(critereRecherche).subscribe(() => {
          this.rechargementChargement();
        });
      } else {
        this.chargementService.supprimerById(this.selection.selected).subscribe(() => {
        });
      }
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
      this.rechargementChargement();
    }
  }

  getItemSize() {
    return 50;
  }
}
