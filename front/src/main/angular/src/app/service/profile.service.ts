import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {UserForEditDto} from "../model/UserForEditDto";
import {UserForResponseDto} from "../model/UserForResponseDto";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  url = environment.appApi + '/api/v1/profile';

  constructor(
      private httpClient: HttpClient
  ) {
  }

  updateUserData(obj: UserForEditDto): Observable<UserForResponseDto> {
    return this.httpClient.put<any>(this.url, obj);
  }

  getProfile(): Observable<UserForResponseDto> {
    return this.httpClient.get<UserForResponseDto>(this.url + '/main');
  }

  getProfileDetails(): Observable<UserForResponseDto> {
    return this.httpClient.get<UserForResponseDto>(this.url + '/details');
  }

  // getPublic(id: number): Observable<Chitchat> {
  //   return this.httpClient.get<Chitchat>(this.url + '/all/' + id);
  // }
  //
  // addUserInChat(user_id: number, chitchat_id: number): Observable<Chitchat> {
  //   const urla = this.url + '/' + chitchat_id + '?userId=' + user_id;
  //   console.log(urla);
  //   return this.httpClient.put<Chitchat>(urla, null);
  // }
  //
  // getAll(): Observable<Chitchat[]> {
  //   return this.httpClient.get<Chitchat[]>(this.url + "/all");
  // }
  //
  // update(obj: Chitchat): Observable<Chitchat> {
  //   return this.httpClient.put<Chitchat>(this.url+'/',obj);
  // }
  //
  // filter(filteredLanguage: string, filteredLevel: string,
  //        filteredDateFrom: string, filteredDateTo: string,
  //        category: Category | null): Observable<Chitchat> {
  //   return this.httpClient.get<Chitchat>(this.url + "/all" +
  //       "?languageId=" + filteredLanguage +
  //       "&levelId=" + filteredLevel +
  //       "&dateFrom=" + filteredDateFrom +
  //       "&dateTo=" + filteredDateTo +
  //       "&categoryId=" + (category ? category.id : ''));
  // }

}

