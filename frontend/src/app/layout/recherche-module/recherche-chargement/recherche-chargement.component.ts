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
  // les noms des colones  'Date Modification',
  displayedColumns: string[] =['depot','exploitation', 'destination', 'site','vehicule','transporteur','categorie'
  ,'poids','poidsSubst','volumeSubst','ecart'];
  constructor(public appConfig: AppConfigService,public chargementService:ChargementService) {
   // this.initDisplayColumn();
  }
  ngOnInit() {

   this.chargementService.getAllChargements().subscribe()

    this.chargementService.chargements$.subscribe((chargements) => {
      if(chargements.length!==null){
        console.log("les chargement: ", chargements);
        //alimentation du tableau
        this.rechercheChargements=chargements
        this.listChargements = new MatTableDataSource<Chargement>(this.rechercheChargements);
        this.listChargements.paginator=this.paginator;
        this.listChargements.sort=this.sort;
      }
      })

  }

  ouvreNouveauChargement(){

  }
  initDisplayColumn(){
   // this.displayedColumns.push('num')
    this.displayedColumns.push('exploitation')
    this.displayedColumns.push('destination')
    this.displayedColumns.push('site')
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
}
