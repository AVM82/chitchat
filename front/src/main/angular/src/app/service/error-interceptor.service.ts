import {Injectable} from '@angular/core';
import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {TokenStorageService} from "./token-storage.service";
import {NotificationService} from "./notification.service";
import {catchError, Observable, switchMap, throwError} from "rxjs";
import {Router} from "@angular/router";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService implements HttpInterceptor {

  private isRefreshing = false;

  constructor(private tokenService: TokenStorageService,
              private router: Router,
              private authService: AuthService,
              private tokenStorageService: TokenStorageService,
              private notificationService: NotificationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(err => {

      if (err.status === 403) {
        console.log("error 403");
        //this.tokenService.logOut();
        this.notificationService.showSnackBar("403");
        // window.location.reload();
      }

      if (err.status === 401) {
        //this.tokenService.logOut();
        console.log("error 401");
        this.notificationService.showSnackBar("401");
        // window.location.reload();
        if (!this.tokenStorageService.getRefreshToken()) {
          console.log('no refresh token');
          this.tokenStorageService.logOut();
        } else { // Checking the type of error required
          if (this.isRefreshing) {
            return next.handle(this.addToken(req));
          } else {
            this.isRefreshing = true;

            return this.authService.refreshToken(
                this.tokenStorageService.getRefreshToken() || '').pipe( // call some function to update the token
                switchMap((value) => {
                  this.tokenStorageService.saveToken(value.token);
                  this.tokenStorageService.saveRefreshToken(value.refreshToken);

                  console.log("resend new request with new token", value.token);
                  this.isRefreshing = false;
                  return next.handle(this.addToken(req)); // Re-invoking a failed request with an updated token
                }),
                catchError((error) => {
                  this.isRefreshing = false;
                  console.log('error of refresh token');
                  this.tokenStorageService.logOut();
                  return throwError(() => error);
                })
            );
          }
        }
        return throwError(err); // Forwarding unhandled errors further
      }


      const error = err.error.message || err.statusText;
      console.log(err);
      this.notificationService.showSnackBar(error);
      return throwError(() => error);
    }));
  }

  private addToken(req: HttpRequest<any>) {
    return req.clone({
      setHeaders: {
        Authorization: `Bearer ${this.tokenStorageService.getToken()}`,
      },
    });
  }
}

export const authErrorInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptorService, multi: true}
];

