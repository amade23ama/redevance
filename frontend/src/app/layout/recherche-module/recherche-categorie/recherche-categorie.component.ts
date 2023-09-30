import {Component, OnInit, ViewChild} from "@angular/core";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Router} from "@angular/router";
import {AutocompleteRechercheService} from "../../../core/services/autocomplete.recherche.service";
import {Categorie} from "../../../core/interfaces/categorie";
import {CategorieService} from "../../../core/services/categorie.service";
import {FormControl} from "@angular/forms";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {DatePipe} from "@angular/common";
import {BuilderDtoJsonAbstract} from "../../../core/interfaces/BuilderDtoJsonAbstract";
import {debounceTime, distinctUntilChanged, Observable, switchMap} from "rxjs";
import {AutocompleteRecherche} from "../../../core/interfaces/autocomplete.recherche";
import {CritereRecherche} from "../../../core/interfaces/critere.recherche";


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
  pageSizeOptions: number[] = [5, 10, 20];
  pageSize = 5; // nb ligne par page par défaut
  rechercheCategogies: Categorie[] = [];
  displayedColumns: string[] =['type', 'volume','dateCreation','actions'];
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesCategorie$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesCategorie$
  constructor(public appConfig: AppConfigService, public categorieService: CategorieService,
              private router:Router,private autocompleteRechercheService:AutocompleteRechercheService) {
  }
  ngOnInit(): void {
    this.rechargementCategorie()
    /** appel du service rechercherVehicules pour recupérer toutes les véhicules en base */
    this.categorieService.categories$.subscribe((data) =>{
      //alimentation du tableau
      this.listCategorie = new MatTableDataSource<Categorie>(data);
      this.listCategorie.paginator=this.paginator;
      this.listCategorie.sort=this.sort;
    });
    this.search.valueChanges?.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((capture) => {
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
          page :1,
          size :20,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.categorieService.chargementCategorieParCritere(critereRecherche).subscribe()
      }

    })
  }

}
