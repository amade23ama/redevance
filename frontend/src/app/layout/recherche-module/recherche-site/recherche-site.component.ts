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
   displayedColumns: string[] = ['Nom', 'Localite', 'Date Creation','actions'];
 sites$=this.siteService.sites$
  /** site Service */
  constructor(public appConfig: AppConfigService, public siteService: SiteService,private router:Router){}


  ngOnInit(): void {
    this.siteService.getAllSites().subscribe();
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
}
