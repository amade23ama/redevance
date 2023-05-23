import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UtilisateurActionsComponent } from './utilisateur-actions.component';

describe('UtilisateurActionsComponent', () => {
  let component: UtilisateurActionsComponent;
  let fixture: ComponentFixture<UtilisateurActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UtilisateurActionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UtilisateurActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
