import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DscomMessageComponent } from './dscom-message.component';

describe('DscomMessageComponent', () => {
  let component: DscomMessageComponent;
  let fixture: ComponentFixture<DscomMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DscomMessageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DscomMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
