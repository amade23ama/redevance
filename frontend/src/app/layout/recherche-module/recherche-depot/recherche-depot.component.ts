import {Component, OnInit, ViewChild} from '@angular/core';
import {AppConfigService} from "../../../core/services/app-config.service";
import {ExploitationService} from "../../../core/services/exploitation.service";
import {Router} from "@angular/router";
import {FormControl} from "@angular/forms";
import {DepotService} from "../../../core/services/depot.service";
import {MatTableDataSource} from "@angular/material/table";
import {Site} from "../../../core/interfaces/site";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Depot} from "../../../core/interfaces/depot";
import {DatePipe} from "@angular/common";
import {BuilderDtoJsonAbstract} from "../../../core/interfaces/BuilderDtoJsonAbstract";

@Component({
  selector: 'app-recherche-depot',
  templateUrl: './recherche-depot.component.html',
  styleUrls: ['./recherche-depot.component.scss']
})
export class RechercheDepotComponent implements OnInit{
  search:FormControl =new FormControl('');
  displayedColumns: string[] = ['id', 'nom','station', 'dateHeureDepot','dateHeureFinDepot','statut','deposeur','nbChargementErreur'
  ,'nbChargementDeposes'];
  depots$=this.depotService.depots$;
  listDepots: MatTableDataSource<Depot>;
  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // nombre de ligne par page
  pageSizeOptions: number[] = [5, 10, 20];
  pageSize = 5; // nb ligne par page par défaut
  constructor(public appConfig: AppConfigService,private router:Router,public depotService:DepotService) {
  }
  ouvreNouveauDepot(){
    this.router.navigate(['depot/creer'])
  }

  ngOnInit(): void {
    this.depotService.getAllDepots().subscribe()
    this.depotService.depots$.subscribe((depots) => {
      console.log("les sites: ", depots);
      //alimentation du tableau
      this.listDepots = new MatTableDataSource<Depot>(depots);
      this.listDepots.paginator=this.paginator;
      this.listDepots.sort=this.sort;
    })
  }
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }
}
