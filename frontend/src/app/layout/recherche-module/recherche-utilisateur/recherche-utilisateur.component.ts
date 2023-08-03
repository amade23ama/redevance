import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {UtilisateurService} from "../../../core/services/utilisateur.service";
import {AppConfigService} from "../../../core/services/app-config.service";
import {BuilderDtoJsonAbstract} from "../../../core/interfaces/BuilderDtoJsonAbstract";
import {DatePipe} from "@angular/common";
import {Observable} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {BreakpointObserver} from "@angular/cdk/layout";
import {Router} from "@angular/router";
import {ParamService} from "../../../core/services/param.service";
import {Profil} from "../../../core/interfaces/profil";

@Component({
  selector: 'app-recherche-utilisateur',
  templateUrl: './recherche-utilisateur.component.html',
  styleUrls: ['./recherche-utilisateur.component.scss']
})
export class RechercheUtilisateurComponent implements OnInit{
  dataSource: MatTableDataSource<Utilisateur>;
  prenom="prenom"
  pageSizeOptions: number[] = [5, 10, 20];
  rechercheUtilisateurListe: Utilisateur[] = [];
  displayedColumns: string[] = ['id','prenom', 'nom', 'email','profil',"actions"];
  pageSize = 5;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  users$=this.utilisateurService.utilisateurs$
  profils$ = this.paramService.profils$;
  profil:Profil[]=[];
constructor(public appConfig: AppConfigService,private readonly utilisateurService: UtilisateurService,
            private router:Router,private paramService: ParamService) {
}
  ngOnInit(): void {
    this.utilisateurService.getAllUtilisateur().subscribe()
    this.paramService.chargementProfils().subscribe()
    this.utilisateurService.utilisateurs$.subscribe((res)=>{
      if (res !== null) {
        this.rechercheUtilisateurListe = res;
        this.dataSource= new MatTableDataSource<Utilisateur>(this.rechercheUtilisateurListe);
        this.dataSource.paginator=this.paginator
        this.dataSource.sort=this.sort
      }
    })
  }
  /**
   * Méthode de formatage de la date
   * @param dateCreation
   */
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE)).transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLE);
  }

  convertToLowercase(text:string){
     this.profils$.subscribe((res)=>{
       this.profil=res;
     })

    if(this.profil){
      return this.profil.filter((res)=>res?.code==text)[0]?.libelle.toLowerCase();
    }
    return text.toLowerCase();
  }



  chargerUtilisateur(utilisteur:Utilisateur){
    this.router.navigate(['admin/utilisateur'], {queryParams: {'contextInfo':utilisteur.id }});
}
  ouvreNouveauUtilisateur(){
    this.utilisateurService.purgerUtilisateur()
    this.router.navigate(['admin/utilisateur']);
  }
  initial(utilisteur:Utilisateur){
    return utilisteur.prenom.charAt(0).concat(utilisteur.nom.charAt(0)).toUpperCase()
  }

}
