import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {UtilisateurService} from "../../../core/services/utilisateur.service";
import {AppConfigService} from "../../../core/services/app-config.service";
import {BuilderDtoJsonAbstract} from "../../../core/interfaces/BuilderDtoJsonAbstract";
import {DatePipe} from "@angular/common";
import {debounceTime, distinctUntilChanged, Observable, of, switchMap} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {BreakpointObserver} from "@angular/cdk/layout";
import {Router} from "@angular/router";
import {ParamService} from "../../../core/services/param.service";
import {Profil} from "../../../core/interfaces/profil";
import {FormControl} from "@angular/forms";
import {Filtre} from "../../../core/interfaces/filtre";
import {AutocompleteRecherche} from "../../../core/interfaces/autocomplete.recherche";
import {AutocompleteRechercheService} from "../../../core/services/autocomplete.recherche.service";
import {CritereRecherche} from "../../../core/interfaces/critere.recherche";

@Component({
  selector: 'app-recherche-utilisateur',
  templateUrl: './recherche-utilisateur.component.html',
  styleUrls: ['./recherche-utilisateur.component.scss']
})
export class RechercheUtilisateurComponent implements OnInit{
  search:FormControl =new FormControl('');
  label="Ajouter un Utilisateur";
  dataSource: MatTableDataSource<Utilisateur>;
  prenom="prenom"
  pageSizeOptions: number[] = [5, 10, 20];
  rechercheUtilisateurListe: Utilisateur[] = [];
  displayedColumns: string[] = ['id','prenom', 'nom', 'email','profil','dateCreation',"actions"];
  pageSize = 5;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  users$=this.utilisateurService.utilisateurs$
  profils$ = this.paramService.profils$;
  profil:Profil[]=[];
  filtres:Filtre[]=[]
  recherche: AutocompleteRecherche[] = [];
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherches$
  critereRecherches$=this.autocompleteRechercheService.critereRecherches$

  constructor(public appConfig: AppConfigService,private readonly utilisateurService: UtilisateurService,
            private router:Router,private paramService: ParamService,
            private autocompleteRechercheService:AutocompleteRechercheService) {
}
  ngOnInit(): void {
    //this.utilisateurService.getAllUtilisateur().subscribe()
    this.paramService.chargementProfils().subscribe()
    this.utilisateurService.utilisateurs$.subscribe((res)=>{
      if (res !== null) {
        this.rechercheUtilisateurListe = res;
        this.dataSource= new MatTableDataSource<Utilisateur>(this.rechercheUtilisateurListe);
        this.dataSource.paginator=this.paginator
        this.dataSource.sort=this.sort
      }
    })

    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        console.log("capture",capture)
          return this.autocompleteRechercheService.autocompleteUtilisateur(capture);
      })
    ).subscribe();
    this.rechargementUtilisateur();
  }
  /**
   * Méthode de formatage de la date
   * @param dateCreation
   */
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
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
  supprimer(filtre:Filtre){
    const index = this.filtres.indexOf(filtre);
    if (index >= 0) {
      this.filtres.splice(index, 1);
    }
  }

  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRecherche(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRecherche(autocompleteRecherche)
  }
  rechargementUtilisateur(){
    this.critereRecherches$.subscribe((res)=>{
      if(res) {
        const critereRecherche   = {
        autocompleteRecherches:res,
        page :1,
        size :20,
        dateDebut :new Date(),
        dateFin :new Date(),
      } as CritereRecherche
        this.utilisateurService.chargementUtilisateurParCritere(critereRecherche).subscribe()
      }

    })
  }
}
