import {Injectable} from "@angular/core";

@Injectable({
  providedIn:'root'
})
export class SiteService {
  siteService() {
    throw new Error('Method not implemented.');
  }
  constructor() {
  }
  //site : string, localite : string
  testMyFunction(data : object){
    console.log("Le service site a récupéré les données :" , data)
  }
}