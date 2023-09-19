import {Component, OnInit, ViewChild} from "@angular/core";
import {ChargementService} from "../../../core/services/chargement.service";
import {FormControl} from "@angular/forms";
import {MatTableDataSource} from "@angular/material/table";
import {Site} from "../../../core/interfaces/site";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Chargement} from "../../../core/interfaces/chargement";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {DatePipe} from "@angular/common";
import {BuilderDtoJsonAbstract} from "../../../core/interfaces/BuilderDtoJsonAbstract";
import {Router} from "@angular/router";
import {AutocompleteRechercheService} from "../../../core/services/autocomplete.recherche.service";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs";
import {AutocompleteRecherche} from "../../../core/interfaces/autocomplete.recherche";
import {CritereRecherche} from "../../../core/interfaces/critere.recherche";

@Component({
  selector: 'app-recherche-chargement',
  templateUrl: './recherche-chargement.component.html',
  styleUrls: ['./recherche-chargement.component.scss']
})
export  class RechercheChargementComponent implements  OnInit{
  chargements$=this.chargementService.chargements$
  search:FormControl =new FormControl('');
  /** la liste des véhicules */
  listChargements: MatTableDataSource<Chargement>;
  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // nombre de ligne par page
  pageSizeOptions: number[] = [5, 10, 20];
  pageSize = 5; // nb ligne par page par défaut
  rechercheChargements: Chargement[] = [];
  // les noms des colones  'Date Modification',,'categorie'
  displayedColumns: string[] =['datePesage', 'site','produit','exploitation', 'destination','vehicule','transporteur'
  ,'poids','poidsSubst','volumeSubst','ecart','actions'];
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesChargement$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesChargement$
  constructor(public appConfig: AppConfigService,public chargementService:ChargementService,
              private router:Router, private autocompleteRechercheService:AutocompleteRechercheService) {
   // this.initDisplayColumn();
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
  initDisplayColumn(){
   // this.displayedColumns.push('num')
    this.displayedColumns.push('exploitation')
    this.displayedColumns.push('destination')
    this.displayedColumns.push('site')
    this.displayedColumns.push('produit')
    //this.displayedColumns.push('transporteur')
    //this.displayedColumns.push('vehicule')
    //this.displayedColumns.push('classe')
    //this.displayedColumns.push('poids')
    //this.displayedColumns.push('PoidsMax')
    //this.displayedColumns.push('PoidsEstime')
    //this.displayedColumns.push('volumeEstime')



     // ['exploitation', 'destination', 'site','transporteur',
   // 'vehicule','classe','poids','PoidsMax','PoidsEstime','volumeEstime','ecart']//,'vmMoyen'];
  }
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }
  chargerChargement(element:Chargement){

  }
  export(){

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
        this.chargementService.chargementChargementParCritere(critereRecherche).subscribe()
      }

    })
  }
}
