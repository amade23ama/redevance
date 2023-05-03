import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, defer, Observable, throwError} from "rxjs";
import {AuthService} from "../services/auth.service";
import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
@Injectable({
  providedIn: 'root'
})
export class IntercepteurService implements HttpInterceptor{
  constructor(private auth: AuthService,private router:Router) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.auth.getToken()) {
      return next.handle(req)
    }
   // const authHeaderHandle = defer(() => {
    const token =this.auth.getToken()
    if (!!token) {
      req = req.clone({
        setHeaders: {
          Authorization:token
        },
        //withCredentials: true
      });
    }
    return next.handle(req)
  //});
   /* return authHeaderHandle.pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // redirect to the login page
          // or refresh the access token and retry the request
          this.auth.clearSession();
          this.router.navigate(["/deconnexion"]);
        }
        else if (error.status === 404) {
          // handle 404 errors
        }
        else {
          // log and handle other errors
        }
        return throwError(error);
      })
    );
    */
  }

}
