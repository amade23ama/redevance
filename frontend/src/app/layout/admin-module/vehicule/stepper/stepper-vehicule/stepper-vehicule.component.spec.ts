import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StepperVehiculeComponent } from './stepper-vehicule.component';

describe('StepperVehiculeComponent', () => {
  let component: StepperVehiculeComponent;
  let fixture: ComponentFixture<StepperVehiculeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StepperVehiculeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StepperVehiculeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
