import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Actions } from 'src/app/core/enum/actions';
import { ActionBtn } from 'src/app/core/interfaces/actionBtn';
import { Chargement } from 'src/app/core/interfaces/chargement';
import { Produit } from 'src/app/core/interfaces/produit';
import { AppConfigService } from 'src/app/core/services/app-config.service';
import { ChargementService } from 'src/app/core/services/chargement.service';
import { ModalService } from 'src/app/core/services/modal.service';
import { ProduitService } from 'src/app/core/services/produit.service';
import { UrlService } from 'src/app/core/services/url.service';

interface Food {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-chargement',
  templateUrl: './chargement.component.html',
  styleUrls: ['./chargement.component.scss']
})
export class ChargementComponent implements OnInit {

    /** la liste des Produits */
    listProduit: Produit[];

  // déclaration: Chargement
  chargementCourant: Chargement;
  destination: FormControl = new FormControl('',[Validators.required])
  date: FormControl = new FormControl({value:"", disabled: true})
  poidsSubst: FormControl = new FormControl({value:"", disabled: true})
  volumeSubst: FormControl = new FormControl({value:"", disabled: true})
  volumeMoyen: FormControl = new FormControl({value:"", disabled: true})

  // déclaration: exploitation
  origine: FormControl = new FormControl({value:"", disabled: true})
  // déclaration: site
  sitePassage: FormControl = new FormControl({value:"", disabled: true})
  // déclaration: voiture
  immatriculation: FormControl = new FormControl({value:"", disabled: true})
  nomTransporteur: FormControl = new FormControl({value:"", disabled: true})
  telTransporteur: FormControl = new FormControl({value:"", disabled: true})
  classe: FormControl = new FormControl({value:"", disabled: true})
  // déclaration: produit
  nomProduit: FormControl = new FormControl('',[Validators.required])

   /** Site exploitation form */
   exploitation: FormGroup = this.fb.group({
    nom: this.origine,
  })

  /** Site passage form */
  site: FormGroup = this.fb.group({
    nom: this.sitePassage,
  })

  /** transporteur form */
  transporteur: FormGroup = this.fb.group({
    nom: this.nomTransporteur,
    telephone: this.telTransporteur,
  })

  /** vehicule form */
  categorie: FormGroup = this.fb.group({
    type: this.classe,
  })

  /** vehicule form */
  produit: FormGroup = this.fb.group({
    nomSRC: this.nomProduit,
  })

  /** vehicule form */
  vehicule: FormGroup = this.fb.group({
    immatriculation: this.immatriculation,
    //transporteur: this.transporteur,
    categorie: this.categorie,
  })

  /** selectedProduit */
  selectedProduit: Produit;


  /** chargement form */
  chargementform: FormGroup = this.fb.group({
    destination: this.destination,
    datePesage: this.date,
    poidsSubst: this.poidsSubst,
    volumeSubst: this.volumeSubst,
    volumeMoyen: this.volumeMoyen,
    exploitation: this.exploitation,
    site: this.site,
    vehicule: this.vehicule,
    produit: this.produit,
    transporteur: this.transporteur,
  })

  /** ActionBtn */
  btns: ActionBtn[] = [];
  datePessage = new Date();

  /** constructor */
  constructor(public appConfig: AppConfigService, private readonly activatedRoute: ActivatedRoute, public urlService: UrlService,
    private chargementService: ChargementService, private fb: FormBuilder, public modalService: ModalService,
    public produitService: ProduitService,){
      this.produitService.rechercherProduits().subscribe();
    }

  /** ngOnInit */
  ngOnInit(): void {

    this.produitService.produits$.subscribe((data) => {
      this.listProduit = data;
    });

    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        this.chargementService.getChargementParId(params['contextInfo']).subscribe(()=>{
          this.chargementCourant =this.chargementService.getChargementCourant();
          this.chargementform.patchValue(this.chargementCourant);
          this.majBtnActive();
        })
      }
    });
    //maj bouton
    this.majBtnActive()
    //init bouton
    this.initListbtns();

  }

  /** initListbtns */
  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'), Actions.ANNULER, true, false, false, true, 'keyboard_arrow_left'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'), Actions.MODIFIER, true, true, true, true, 'create'));
    return this.btns;
  }

  /** Action sur les boutons Enregistrer ou ANNULER */
  chargementAction(event: Actions){
    //Le click sur le bouton ENREGISTRER
    if (event === Actions.MODIFIER) {
      let chargementAmodifier = this.chargementCourant;
      //le produit
      let newProduit = new Produit();
      newProduit.nomSRC = this.getProduit.value;
      newProduit.nomNORM = this.getProduit.value;
      // la destination
      chargementAmodifier.destination = this.getDestination?.value
      chargementAmodifier.produit = newProduit;
      this.chargementService.modifierChargement(chargementAmodifier).subscribe((data) => {
        this.chargementform.patchValue(data);
        this.modalService.ouvrirModalConfirmation('Le chargement a été bien modifié.' );
      });
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.modalService.ouvrirModaleAnnulation(this.urlService.getPreviousUrl(), 'modification de chargement'); //Ouverture de la modale d'annulation
    }
  }

  /**
   * reset
   * @param formToReset
   */
  reset(formToReset:any){
    this.chargementform.controls[formToReset]?.setValue('');
  }

  // Activation et désactivation des boutons en fonction des actions de l'utilisateur
  majBtnActive(){
    // Formulaire non valid
    this.chargementform?.valueChanges.subscribe((res)=>{
      if(this.chargementform.invalid){
            this.majBtnState(Actions.MODIFIER, true, true);

      }

      // Formulaire valid
      if(!this.chargementform.invalid){
            this.majBtnState(Actions.MODIFIER, false, true);
      }
    })

  }

    /** ouvrir Modale Annulation */
    majBtnState(a: Actions, disabled: boolean, display: boolean) {
      this.btns.forEach(b => {
        if (b.id === a) {
          b.disabled = disabled;
          b.display = display;
        }
      });
    }
  
  // get data
  get getDestination(): AbstractControl { return this.chargementform?.get("destination");}
  get getProduit(): AbstractControl { return this.produit?.get("nomSRC");}

}
