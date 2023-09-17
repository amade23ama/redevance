import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Site } from 'src/app/core/interfaces/site';
import { SiteService } from 'src/app/core/services/site.service';
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {Router} from "@angular/router";
import {AppConfigService} from "../../../core/services/app-config.service";
import {FormControl} from "@angular/forms";
import {Exploitation} from "../../../core/interfaces/exploitation";
import {DatePipe} from "@angular/common";
import {BuilderDtoJsonAbstract} from "../../../core/interfaces/BuilderDtoJsonAbstract";
import {AutocompleteRechercheService} from "../../../core/services/autocomplete.recherche.service";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs";
import {AutocompleteRecherche} from "../../../core/interfaces/autocomplete.recherche";
import {CritereRecherche} from "../../../core/interfaces/critere.recherche";

@Component({
  selector: 'recherche-site',
  templateUrl: './recherche-site.component.html',
  styleUrls: ['./recherche-site.component.scss']
})
export class RechercheSiteComponent implements OnInit {
  search:FormControl =new FormControl('');
   /** la liste des véhicules */
   listSites: MatTableDataSource<Site>;

   // La pagination
   @ViewChild(MatPaginator) paginator: MatPaginator;
   @ViewChild(MatSort) sort: MatSort;

   // nombre de ligne par page
   pageSizeOptions: number[] = [5, 10, 20];
   pageSize = 5; // nb ligne par page par défaut

   // les noms des colones  'Date Modification',
   displayedColumns: string[] = ['nom','localite','dateCreation','actions'];
 sites$=this.siteService.sites$
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesSite$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesSite$
  /** site Service */
  constructor(public appConfig: AppConfigService, public siteService: SiteService,private router:Router,
              private autocompleteRechercheService:AutocompleteRechercheService){}


  ngOnInit(): void {
   // this.siteService.getAllSites().subscribe();
    this.rechargementSite()
    this.siteService.getCompteurSites().subscribe();
    this.siteService.sites$.subscribe((sites) => {
      console.log("les sites: ", sites);
      //alimentation du tableau
    this.listSites = new MatTableDataSource<Site>(sites);
    this.listSites.paginator=this.paginator;
    this.listSites.sort=this.sort;
    })

    this.siteService.nbSites$.subscribe((nb) => {
      console.log("le nombre de site: ", nb);
    })

    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        return this.autocompleteRechercheService.autocompleteSite(capture);
      })
    ).subscribe();
  }


  chargerSite(site:Site){
    this.router.navigate(['admin/site'], {queryParams: {'contextInfo':site.id }});
  }
  ouvreNouveauSite(){
    this.router.navigate(['admin/site'])
  }
  initial(site:Site){
    return site.nom.charAt(0).toUpperCase()
  }
  /**
   * Méthode de formatage de la date
   * @param dateCreation
   */
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }
  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRechercheSite(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRechercheSite(autocompleteRecherche)
  }

  rechargementSite(){
    this.critereRecherches$.subscribe((res)=>{
      if(res) {
        const critereRecherche   = {
          autocompleteRecherches:res,
          page :1,
          size :20,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.siteService.chargementSiteParCritere(critereRecherche).subscribe()
      }
    })
  }
}
