import {Injectable} from '@angular/core';
import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {Observable} from "rxjs";
import {TranslocoService} from "@ngneat/transloco";

@Injectable()
export class LangInterceptorService implements HttpInterceptor {
  locals: {[index: string]:any} = {
    "en": "en-US",
    "de": "de-DE",
    "uk": "uk-UA",
  };

  constructor(private translocoService: TranslocoService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let langRequest = req.clone({headers: req.headers
      .set('Accept-Language', this.locals[this.translocoService.getActiveLang()])});
    return next.handle(langRequest);
  }
}

export const langInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: LangInterceptorService, multi: true}
];

