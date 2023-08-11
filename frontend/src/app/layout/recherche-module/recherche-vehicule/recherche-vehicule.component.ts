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
  displayedColumns: string[] = ['Prenom', 'Nom', 'Téléphone', 'Email', 'Immatriculation', 'Classe', 'Volume'];

  /** constructor */
  constructor(public appConfig: AppConfigService,private vehiculeService: VehiculeService,
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
}
