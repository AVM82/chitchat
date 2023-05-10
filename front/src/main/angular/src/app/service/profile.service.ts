import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {UserForEditDto} from "../model/UserForEditDto";
import {UserForResponseDto} from "../model/UserForResponseDto";
import {Chitchat} from "../model/Chitchat";

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

  getUserCreatedChats(): Observable<Chitchat[]> {
    return this.httpClient.get<Chitchat[]>(this.url + '/my_chitchats');
  }

  getChatsWithUser(): Observable<Chitchat[]> {
    return this.httpClient.get<Chitchat[]>(this.url + '/planned_chitchats');
  }

  getArchiveChats(): Observable<Chitchat[]> {
    return this.httpClient.get<Chitchat[]>(this.url + '/archive_chitchats');
  }
}

