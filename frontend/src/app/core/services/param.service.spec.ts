

import {ParamService} from "./param.service";
import {TestBed} from "@angular/core/testing";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Profil} from "../interfaces/profil";

// @ts-ignore
const profilsMock: Profil[] = [ {code:"admin",libelle:"string"}];
 class ParamServiceMock extends ParamService{


  chargementProfils():Observable<Profil[]> {
    return of(profilsMock)
  }
  }

describe('ParamService', () => {
  let service: ParamService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule,],
      providers: [
        {provide: ParamService, useClass: ParamServiceMock}
  ]
    });
    service = TestBed.inject(ParamService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should be chargementProfils', () => {
    let result: Profil[];
    service.chargementProfils().subscribe((profils) => {
      result = profils;
    });
    expect(result).toEqual(profilsMock);
  });
});
