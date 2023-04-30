import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category} from "../model/Category";
import {Observable} from "rxjs";
import {Language} from "../model/Language";
import {Level} from "../model/Level";

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  url = '/api/v1/languages';

  constructor(private httpClient: HttpClient) {}

  getAll(): Observable<Language[]> {
    return this.httpClient.get<Language[]>(this.url+"/all");
  }

  getLevels(): Observable<Level[]> {
    return this.httpClient.get<Level[]>(this.url+"/levels");
  }
}
