import {BehaviorSubject} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  /**
   * Nombre de requetes.
   */
  public numberOfRequests = 0;
  /**
   * Affiche le spinner.
   */
  public showSpinner: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  /**
   * Gere le nombre de requetes en cours.
   * @param state
   */
  handleRequest = (state: string = 'minus'): void => {
    this.numberOfRequests = (state === 'plus') ? this.numberOfRequests + 1 : this.numberOfRequests - 1;
    this.showSpinner.next(this.numberOfRequests > 0);
  }
}
