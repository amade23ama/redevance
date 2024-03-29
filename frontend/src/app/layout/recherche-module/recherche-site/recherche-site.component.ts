import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from "@angular/router";
import { debounceTime, distinctUntilChanged, switchMap } from "rxjs";
import { Site } from 'src/app/core/interfaces/site';
import { ModalService } from "src/app/core/services/modal.service";
import { SiteService } from 'src/app/core/services/site.service';
import { BuilderDtoJsonAbstract } from "../../../core/interfaces/BuilderDtoJsonAbstract";
import { AutocompleteRecherche } from "../../../core/interfaces/autocomplete.recherche";
import { CritereRecherche } from "../../../core/interfaces/critere.recherche";
import { AppConfigService } from "../../../core/services/app-config.service";
import { AutocompleteRechercheService } from "../../../core/services/autocomplete.recherche.service";
import { SuppressionComponent } from "../../shared-Module/dialog/suppression/suppression.component";

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
   // les noms des colones  'Date Modification',
   displayedColumns: string[] = ['id','nom','localite','dateCreation','actions'];
 sites$=this.siteService.sites$;
 nb$=this.siteService.nbSites$;
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesSite$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesSite$
  /** site Service */
  constructor(public appConfig: AppConfigService, public siteService: SiteService,private router:Router, private modalService: ModalService,
              private dialog: MatDialog, private autocompleteRechercheService:AutocompleteRechercheService){}


  ngOnInit(): void {
    this.siteService.setSites([]);
    this.rechargementSite();
    this.siteService.sites$.subscribe((sites) => {
      //alimentation du tableau
    this.listSites = new MatTableDataSource<Site>(sites);
    //this.listSites.paginator=this.paginator;
    this.listSites.sort=this.sort;
    this.itemSize=sites.length;
    this.totalItems = 100;
    })

    this.siteService.nbSites$.subscribe((nb) => {})

    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        this.page=0
        this.newPage=0;
        this.croll=false;
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
          page :this.newPage,
          size :this.size,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.siteService.chargementSiteParCritere(critereRecherche, this.croll).subscribe()
      }
    })
  }

  /**
   * supprimerSite
   * @param site
   */
  supprimerSite(site: Site){

    const dialogRef = this.dialog.open(SuppressionComponent, {
      width: '600px',
      position: {top:'200px'},
      data: {name: "le site ".concat(site.nom), id: site.id},
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.siteService.supprimerSite(site.id).subscribe((idDelete) => {
          if (idDelete) {
            this.rechargementSite();
            this.modalService.ouvrirModalConfirmation("Site supprimé")
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
      this.rechargementSite();
    }
  }

  getItemSize() {
    return 50;
  }
}
