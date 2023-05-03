import {CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";
@Injectable({
  providedIn: 'root'
})
export class RouteGuard implements CanActivate {
  constructor(
    private auth: AuthService,
    private router: Router
  ) {}
  canActivate():boolean {
      const login=this.auth.isLoggedIn()
    const isAllow = this.auth.isLoggedIn();

    if (isAllow) {
      return true;
    }
    this.router.navigate(['login'])
    return false
  }

}
