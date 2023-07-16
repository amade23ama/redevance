import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Site } from 'src/app/core/interfaces/site';
import { SiteService } from 'src/app/core/services/site.service';

@Component({
  selector: 'recherche-site',
  templateUrl: './recherche-site.component.html',
  styleUrls: ['./recherche-site.component.css']
})
export class RechercheSiteComponent implements OnInit {

   /** la liste des véhicules */
   listSites: MatTableDataSource<Site>;

   // La pagination
   @ViewChild(MatPaginator) paginator: MatPaginator;
   @ViewChild(MatSort) sort: MatSort;
 
   // nombre de ligne par page
   pageSizeOptions: number[] = [5, 10, 20];
   pageSize = 5; // nb ligne par page par défaut
 
   // les noms des colones
   displayedColumns: string[] = ['Nom', 'Localite', 'Date Creation', 'Date Modification'];

  /** site Service */
  constructor(private siteService: SiteService){}

  
  ngOnInit(): void {
    this.siteService.getAllSites().subscribe();
    this.siteService.getCompteurSites().subscribe();
    this.siteService.getSiteById(1).subscribe();

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

    this.siteService.siteById$.subscribe((nb) => {
      console.log("site by id: ", nb);
    })

    //Suppression site
    //this.siteService.supprimerSite(2).subscribe();

    // enregistrement
    /*let site = new Site();
    site.id = 2;
    site.dateCreation = new Date();
    site.dateModification = new Date();
    site.localite = "Thies3";
    site.nom = "THIES";
    this.siteService.enregistrerSite(site).subscribe();*/

  }

  redirect(site: Site) {
    console.log("ertyhjk", site);
  }

}
