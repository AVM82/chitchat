import { Injectable } from '@angular/core';
import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {TokenStorageService} from "./token-storage.service";
import {NotificationService} from "./notification.service";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService implements HttpInterceptor {

  constructor(private tokenService: TokenStorageService,
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
      }


      const error = err.error.message || err.statusText;
      console.log(err);
      this.notificationService.showSnackBar(error);
      return throwError(() => error);
    }));
  }
}

export const authErrorInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptorService, multi: true}
];

