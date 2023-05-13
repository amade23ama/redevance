import {AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {StepperTransporteurComponent} from "./stepper-transporteur/stepper-transporteur.component";
import {StepperVehiculeComponent} from "./stepper-vehicule/stepper-vehicule.component";
import {AppConfigService} from "../../../../core/services/app-config.service";

@Component({
  selector: 'app-stepper',
  templateUrl: './stepper.component.html',
  styleUrls: ['./stepper.component.scss']
})
export class StepperComponent implements OnInit, AfterViewInit  {
  @ViewChild(StepperTransporteurComponent) stepperTransporteurComponent :StepperTransporteurComponent
  @ViewChild(StepperVehiculeComponent) stepperVehiculeComponent:StepperVehiculeComponent


  constructor(private changeDetector: ChangeDetectorRef,
              public appConfig:AppConfigService){}
  ngOnInit(): void {
  this.changeDetector.detectChanges()
  }
  ngAfterViewInit(): void {
  }
  submit(stepperTransporteurComponent:any,stepperVehiculeComponent:any){

  }

}
