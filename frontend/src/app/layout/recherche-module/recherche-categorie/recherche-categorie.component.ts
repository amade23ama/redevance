import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormControl } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { debounceTime, distinctUntilChanged, switchMap } from "rxjs";
import { ModalService } from "src/app/core/services/modal.service";
import { BuilderDtoJsonAbstract } from "../../../core/interfaces/BuilderDtoJsonAbstract";
import { AutocompleteRecherche } from "../../../core/interfaces/autocomplete.recherche";
import { Categorie } from "../../../core/interfaces/categorie";
import { CritereRecherche } from "../../../core/interfaces/critere.recherche";
import { AppConfigService } from "../../../core/services/app-config.service";
import { AutocompleteRechercheService } from "../../../core/services/autocomplete.recherche.service";
import { CategorieService } from "../../../core/services/categorie.service";
import { SuppressionComponent } from "../../shared-Module/dialog/suppression/suppression.component";


@Component({
  selector: 'app-recherche-categorie',
  templateUrl: './recherche-categorie.component.html',
  styleUrls: ['./recherche-categorie.component.scss']
})
export class RechercheCategorieComponent implements  OnInit{
  categories$=this.categorieService.categories$
  search:FormControl =new FormControl('');
  searchDate:FormControl =new FormControl('');
  /** la liste des véhicules */
  listCategorie: MatTableDataSource<Categorie>;
  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // nombre de ligne par page
  //pageSizeOptions: number[] = [10, 20, 30];
  pageSize = 10; // nb ligne par page par défaut
  itemSize: number = 0;
  totalItems = 10;
  page = 0;
  size = 10;
  itemsPerPage = 10;
  newPage = 0
  croll: boolean = false;
  private lastScrollIndex = 0;
  rechercheCategogies: Categorie[] = [];
  nb$=this.categorieService.nbCategories$
  displayedColumns: string[] =['id','type', 'volume','dateCreation','actions'];
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesCategorie$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesCategorie$
  constructor(public appConfig: AppConfigService, public categorieService: CategorieService,
              private dialog: MatDialog, private modalService: ModalService,
              private router:Router,private autocompleteRechercheService:AutocompleteRechercheService) {
  }
  ngOnInit(): void {
    this.categorieService.setCategories([]);
    this.rechargementCategorie()
    /** appel du service rechercherVehicules pour recupérer toutes les véhicules en base */
    this.categorieService.categories$.subscribe((data) =>{
      //alimentation du tableau
      this.listCategorie = new MatTableDataSource<Categorie>(data);
      //this.listCategorie.paginator=this.paginator;
      this.listCategorie.sort=this.sort;
      this.itemSize=data.length
      this.totalItems = 100;
    });
    this.search.valueChanges?.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((capture) => {
        this.page=0
        this.newPage=0;
        this.croll=false;
        return this.autocompleteRechercheService.autocompleteCategorie(capture);
      })
    ).subscribe();
  }
  ouvreNouveauCategorie(){
    this.router.navigate(['admin/categorie'])
  }

  chargerCategorie(categorie: Categorie){
      this.router.navigate(['admin/categorie'], {queryParams: {'contextInfo':categorie.id }});
    }
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }


  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRechercheCategorie(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRechercheCategorie(autocompleteRecherche)
  }
  rechargementCategorie(){
    this.critereRecherches$.subscribe((res)=>{
      if(res) {
        const critereRecherche   = {
          autocompleteRecherches:res,
          page :this.newPage,
          size :this.size,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.categorieService.chargementCategorieParCritere(critereRecherche, this.croll).subscribe()
      }

    })
  }

  /**
   * supprimerCategorie
   * @param site
   */
  supprimerCategorie(categorie: Categorie){

    const dialogRef = this.dialog.open(SuppressionComponent, {
      width: '600px',
      position: {top:'200px'},
      data: {name: "la classe ".concat(categorie.type.toString()), id: categorie.id},
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result >= 0) {
        this.categorieService.supprimerCategories(categorie.id).subscribe((idDelete) => {
          if (idDelete) {
            this.rechargementCategorie();
            this.modalService.ouvrirModalConfirmation("La classe a été supprimé")
          }
        });
      }
  });
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
    this.rechargementCategorie();
  }
}

getItemSize() {
  return 50;
}

}
