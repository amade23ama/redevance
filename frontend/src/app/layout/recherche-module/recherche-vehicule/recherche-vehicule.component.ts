import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Vehicule } from 'src/app/core/interfaces/vehicule';
import { VehiculeService } from 'src/app/core/services/vehicule.service';
import {AppConfigService} from "../../../core/services/app-config.service";
import {Site} from "../../../core/interfaces/site";
import {Router} from "@angular/router";
import {FormControl} from "@angular/forms";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {DatePipe} from "@angular/common";
import {BuilderDtoJsonAbstract} from "../../../core/interfaces/BuilderDtoJsonAbstract";

@Component({
  selector: 'recherche-vehicule',
  templateUrl: './recherche-vehicule.component.html',
  styleUrls: ['./recherche-vehicule.component.scss']
})
export class RechercheVehiculeComponent implements OnInit{
  search:FormControl =new FormControl('');
  /** la liste des véhicules */
  listVehicule: MatTableDataSource<Vehicule>;

  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // nombre de ligne par page
  pageSizeOptions: number[] = [5, 10, 20];
  pageSize = 5; // nb ligne par page par défaut

  // les noms des colones
  displayedColumns: string[] = ['NomRS', 'Téléphone', 'Email', 'Immatriculation', 'Classe', 'Volume','dateCreation'];
  vehicules$=this.vehiculeService.vehicules$
  /** constructor */
  constructor(public appConfig: AppConfigService,public vehiculeService: VehiculeService,
              private router:Router) {}


  ngOnInit(): void {

    /** appel du service rechercherVehicules pour recupérer toutes les véhicules en base */
    this.vehiculeService.rechercherVehicules().subscribe((data) =>{
    //alimentation du tableau
    this.listVehicule = new MatTableDataSource<Vehicule>(data);
    this.listVehicule.paginator=this.paginator;
    this.listVehicule.sort=this.sort;
    });

  }

  redirect(vehicule: Vehicule) {
    console.log("ertyhjk", vehicule);
  }
  chargervehicule(vehicule: Vehicule){
    this.router.navigate(['admin/vehicule'], {queryParams: {'contextInfo':vehicule.id }});
  }
  ouvreNouveauVehicule(){
    this.router.navigate(['admin/vehicule'])
  }
  initial(vehicule: Vehicule){
    return vehicule.immatriculation.charAt(0).toUpperCase()
  }
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }
}
