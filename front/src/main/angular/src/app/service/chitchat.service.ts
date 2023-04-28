import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Chitchat} from "../model/Chitchat";
import {TokenStorageService} from "./token-storage.service";

@Injectable({
  providedIn: 'root'
})
export class ChitchatService {
  url = 'http://localhost:5000/api/v1/chitchats';

  constructor(
      private httpClient: HttpClient,
      private tokenStorage: TokenStorageService
      ) {}

  add(obj: Chitchat): Observable<Chitchat> {
    return  this.httpClient.post<Chitchat>(this.url,obj);
  }

  get(id: number): Observable<Chitchat> {
    return this.httpClient.get<Chitchat>(this.url+'/'+id);
  }

  getAll(): Observable<Chitchat[]> {
    return this.httpClient.get<Chitchat[]>(this.url+"/all");
  }

  update(obj: Chitchat): Observable<Chitchat> {
    return this.httpClient.put<Chitchat>(this.url+'/',obj);
  }

}

