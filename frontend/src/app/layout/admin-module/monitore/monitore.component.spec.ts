import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitoreComponent } from './monitore.component';

describe('MonitoreComponent', () => {
  let component: MonitoreComponent;
  let fixture: ComponentFixture<MonitoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MonitoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MonitoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
