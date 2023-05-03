import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheDetailComponent } from './recherche-detail.component';

describe('RechercheDetailComponent', () => {
  let component: RechercheDetailComponent;
  let fixture: ComponentFixture<RechercheDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RechercheDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
