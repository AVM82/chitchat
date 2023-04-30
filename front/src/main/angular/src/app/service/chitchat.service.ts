import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Chitchat} from "../model/Chitchat";

@Injectable({
  providedIn: 'root'
})
export class ChitchatService {
  url = 'http://localhost:5000/api/v1/chitchats';

  constructor(
      private httpClient: HttpClient
      ) {}

  add(obj: Chitchat): Observable<Chitchat> {
    return  this.httpClient.post<Chitchat>(this.url,obj);
  }

  get(id: number): Observable<Chitchat> {
    return this.httpClient.get<Chitchat>(this.url+'/'+id);
  }

  addUserInChat(user_id: number, chitchat_id: number ): Observable<Chitchat> {
     const urla = this.url+'/'+chitchat_id+'?userId='+user_id;
     const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
     console.log(urla);
     return this.httpClient.put<Chitchat>(urla,null);
  }

  getAll(): Observable<Chitchat[]> {
    return this.httpClient.get<Chitchat[]>(this.url+"/all");
  }

  update(obj: Chitchat): Observable<Chitchat> {
    return this.httpClient.put<Chitchat>(this.url+'/',obj);
  }

}

