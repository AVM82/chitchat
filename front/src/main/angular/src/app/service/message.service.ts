import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";

// Declare SockJS and Stomp
declare var SockJS: any;
declare var Stomp: any;
@Injectable({
  providedIn: 'root'
})
export class MessageService {
  url = environment.appApi + '/socket';

  constructor() {
    this.initializeWebSocketConnection();
  }
  public stompClient: any;
  public msg = [];initializeWebSocketConnection() {
    const serverUrl = this.url;
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    // tslint:disable-next-line:only-arrow-functions
    this.stompClient.connect({}, function(frame: any) {
      that.stompClient.subscribe('/message', (message: any) => {
        if (message.body) {
          // @ts-ignore
          that.msg.push(message.body);
        }
      });
    });
  }

  sendMessage(message: any) {
    this.stompClient.send('/app/send/message' , {}, message);
  }
}
