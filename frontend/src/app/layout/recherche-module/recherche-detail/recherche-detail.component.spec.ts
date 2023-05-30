import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheDetailComponent } from './recherche-detail.component';
import {HttpClientModule} from "@angular/common/http";

describe('RechercheDetailComponent', () => {
  let component: RechercheDetailComponent;
  let fixture: ComponentFixture<RechercheDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheDetailComponent ],
      imports: [HttpClientModule],
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
