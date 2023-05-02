import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Chitchat} from "../model/Chitchat";
import {Category} from "../model/Category";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ChitchatService {
  url = environment.appApi + '/api/v1/chitchats';

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
     console.log(urla);
     return this.httpClient.put<Chitchat>(urla,null);
  }

  getAll(): Observable<Chitchat[]> {
    return this.httpClient.get<Chitchat[]>(this.url+"/all");
  }

  update(obj: Chitchat): Observable<Chitchat> {
    return this.httpClient.put<Chitchat>(this.url+'/',obj);
  }

  filter(filteredLanguage: string, filteredLevel: string,
         filteredDateFrom: string, filteredDateTo: string,
         category: Category | null): Observable<Chitchat> {
    return this.httpClient.get<Chitchat>(this.url + "/all" +
        "?languageId=" + filteredLanguage +
        "&levelId=" + filteredLevel +
        "&dateFrom=" + filteredDateFrom +
        "&dateTo=" + filteredDateTo +
        "&categoryId=" + (category ? category.id : ''));
  }

}

