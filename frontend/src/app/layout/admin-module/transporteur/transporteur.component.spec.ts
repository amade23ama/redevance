import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransporteurComponent } from './transporteur.component';

describe('TransporteurComponent', () => {
  let component: TransporteurComponent;
  let fixture: ComponentFixture<TransporteurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransporteurComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransporteurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
