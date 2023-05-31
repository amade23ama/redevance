import {CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";
import {AppConfigService} from "../services/app-config.service";
@Injectable({
  providedIn: 'root'
})
export class RouteGuard implements CanActivate {
  constructor(
    private auth: AuthService,
    private router: Router,
    private appConfig:AppConfigService
  ) {}
  canActivate():boolean {
      const login=this.auth.isLoggedIn()
    const isAllow = this.auth.isLoggedIn();

    if (isAllow) {
      return true;
    }
    this.router.navigate([''], { queryParams: { 'errorMsg': this.appConfig.getLabel("error.unsufisiantDroit") } });
    return false
  }

}
