import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitoreComponent } from './monitore.component';
import {HttpClientModule} from "@angular/common/http";

describe('MonitoreComponent', () => {
  let component: MonitoreComponent;
  let fixture: ComponentFixture<MonitoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MonitoreComponent ],
      imports: [HttpClientModule],
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
