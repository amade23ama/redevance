import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DscomLoginComponent } from './dscom-login.component';

describe('DscomLoginComponent', () => {
  let component: DscomLoginComponent;
  let fixture: ComponentFixture<DscomLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DscomLoginComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DscomLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
