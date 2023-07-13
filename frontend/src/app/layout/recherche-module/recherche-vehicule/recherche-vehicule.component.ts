import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Vehicule } from 'src/app/core/interfaces/vehicule';
import { VehiculeService } from 'src/app/core/services/vehicule.service';

@Component({
  selector: 'recherche-vehicule',
  templateUrl: './recherche-vehicule.component.html',
  styleUrls: ['./recherche-vehicule.component.css']
})
export class RechercheVehiculeComponent implements OnInit{

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
  constructor(private vehiculeService: VehiculeService) {}


  ngOnInit(): void {

    /** appel du service rechercherVehicules pour recupérer toutes les véhicules en base */
    this.vehiculeService.rechercherVehicules().subscribe((data) =>{
    //alimentation du tableau
    this.listVehicule = new MatTableDataSource<Vehicule>(data);
    this.listVehicule.paginator=this.paginator;
    this.listVehicule.sort=this.sort;
    });
 let vehi = new Vehicule();
 vehi.id = 1;
 vehi.immatriculation = "MLT5365k"
    this.vehiculeService.modifierVehicule(vehi).subscribe((toto) => {
      console.log("zertyu", toto);
    });

    
  }

}
