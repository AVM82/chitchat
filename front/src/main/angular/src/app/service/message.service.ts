import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Message} from "../model/Message";
import {ChitchatUnreadCount} from "../model/ChitchatUnreadCount";

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  url = environment.appApi + '/api/v1/chitchats';

  constructor(
      private httpClient: HttpClient
  ) {
  }

  getChitchatAllMessages(id: number): Observable<Message[]> {
    return this.httpClient.get<Message[]>(this.url + '/chat_messages/' + id);
  }

  getTotalCountUnreadUserMessages(): Observable<any> {
    return this.httpClient.get<any>(this.url + '/chat_messages/unread_count');
  }

  getAllUnreadUserChitchats(): Observable<ChitchatUnreadCount[]> {
    return this.httpClient.get<ChitchatUnreadCount[]>(this.url + '/chat_messages/unread_chitchats');
  }
}
