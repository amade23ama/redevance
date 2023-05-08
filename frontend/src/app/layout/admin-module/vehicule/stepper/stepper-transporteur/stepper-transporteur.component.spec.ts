import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StepperTransporteurComponent } from './stepper-transporteur.component';

describe('StepperTransporteurComponent', () => {
  let component: StepperTransporteurComponent;
  let fixture: ComponentFixture<StepperTransporteurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StepperTransporteurComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StepperTransporteurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
