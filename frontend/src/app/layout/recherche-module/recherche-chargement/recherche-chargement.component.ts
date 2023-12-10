import { SelectionModel } from "@angular/cdk/collections";
import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormControl } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
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
import { ModalService } from "../../../core/services/modal.service";
import { ReferenceService } from "../../../core/services/reference.service";
import { SuppressionComponent } from "../../shared-Module/dialog/suppression/suppression.component";

@Component({
  selector: 'app-recherche-chargement',
  templateUrl: './recherche-chargement.component.html',
  styleUrls: ['./recherche-chargement.component.scss']
})
export  class RechercheChargementComponent implements  OnInit{
  chargements$=this.chargementService.chargements$
  search:FormControl =new FormControl('');
  searchDate:FormControl =new FormControl('');
 btnSelectAll:boolean=false;
  /** la liste des véhicules */
  listChargements: MatTableDataSource<Chargement>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  selection = new SelectionModel<Chargement>(true, []);
  disableBtnSupprimer:boolean=true;
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
  nb$=this.chargementService.nbChargements$
  // les noms des colones  'Date Modification',,'categorie'
  displayedColumns: string[] =['numImport' ,'datePesage', 'site','produit','exploitation', 'destination','vehicule','transporteur'
  ,'poids','poidsSubst','volumeSubst','ecart','actions'];
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesChargement$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesChargement$
  constructor(public appConfig: AppConfigService,public chargementService:ChargementService,
              private router:Router, private autocompleteRechercheService:AutocompleteRechercheService,
              public readonly  referenceService:ReferenceService,public modalService: ModalService,
              private dialog: MatDialog,) {
  }
  ngOnInit() {
    this.referenceService.getAllAnnee().subscribe()
    this.chargementService.setChargements([]);
    this.rechargementChargement();
    this.referenceService.getAnneeMax()
    this.searchDate.setValue(this.referenceService.getAnneeMax())

    this.chargementService.chargements$.subscribe((chargements) => {
      if(chargements!==null){
        this.rechercheChargements=chargements
        this.listChargements = new MatTableDataSource<Chargement>(this.rechercheChargements);
        if(this.btnSelectAll){
          this.listChargements.data.forEach(row => this.selection.select(row));
        }
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
    if(this.isAllSelected()){
      this.chargementService.exportDocumentChargementParCritere(critereRecherche).subscribe()
    }else {
      this.chargementService.exportDocumentChargementParIdChargment(this.selection.selected).subscribe()
    }

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
          if (critereRecherche.annee !== this.searchDate.value) {
            this.croll=false
            critereRecherche.annee=this.searchDate.value;
            this.chargementService.chargementChargementParCritere(critereRecherche, this.croll).subscribe()
          }
        })

      //  if ((critereRecherche.annee === this.searchDate.value) || this.croll) {
          critereRecherche.annee=this.searchDate.value;
          this.chargementService.chargementChargementParCritere(critereRecherche, this.croll).subscribe()
          this.croll=false
       // }

      }

    })
  }

  initRechargementChargement(critereRecherche: CritereRecherche){
    this.chargementService.chargementChargementParCritere(critereRecherche, true).subscribe()
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.listChargements.data.length;
    return numSelected === numRows;
  }
  handleHeaderCheckboxToggle(event: any) {
    this.btnSelectAll=event.checked
    if(event.checked){
      this.listChargements.data.forEach(row => this.selection.select(row));
      this.disableBtnSupprimer=false;
    }else {
      this.selection.clear()
      this.disableBtnSupprimer=true;
    }

  }
    checkboxToggle(event: any, chargement: Chargement) {
      this.btnSelectAll=event.checked
      if (event.checked) {
        this.selection.select(chargement);
      } else {
        this.selection.deselect(chargement)
      }
      this.disableBtnSupprimer = this.selection.selected.length > 0 ? false : true;
      this.isAllSelected()
    }
    supprimer(critereRecherches: Observable<AutocompleteRecherche[]>) {
      const dialogRef = this.dialog.open(SuppressionComponent, {
        width: '600px',
        position: {top:'200px'},
        data: {name: this.appConfig.getLabel("label.chargement.selectionner")}
      })
      dialogRef.afterClosed().subscribe(res => {
       if(res){
         this.supprimerchargement(critereRecherches)
       }
      })
    }

  supprimerchargement(critereRecherches: Observable<AutocompleteRecherche[]>) {
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
        this.rechargementChargement();
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

      this.critereRecherches$.subscribe((res)=>{
        if(res) {
          const critereRecherche   = {
            autocompleteRecherches:res,
            page :this.newPage,
            size :this.size,
            dateDebut :new Date(),
            dateFin :new Date(),
          } as CritereRecherche;
          this.initRechargementChargement(critereRecherche);
        }

      })


    }
  }

  getItemSize() {
    return 50;
  }
}
