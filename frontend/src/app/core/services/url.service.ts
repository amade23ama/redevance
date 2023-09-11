import { Injectable } from '@angular/core';
import { Router, RoutesRecognized } from '@angular/router';
import { filter, pairwise } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class UrlService {

  // attributs
  private previousUrl: string;
  private currentUrl: string;

  /** constructor */
  constructor(private router: Router) {
      this.router.events.pipe(filter((evt: any) => evt instanceof RoutesRecognized), pairwise())
                        .subscribe((events: RoutesRecognized[]) => {
                          this.previousUrl = events[0].urlAfterRedirects;
                          console.log('previous url', this.previousUrl);
                        });
  }

  /** getPreviousUrl */
  public getPreviousUrl() {
    return this.previousUrl;
  }    
}
