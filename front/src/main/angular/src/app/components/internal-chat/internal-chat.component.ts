import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {TokenStorageService} from "../../service/token-storage.service";
import {Message} from "../../model/Message";
import {environment} from "../../../environments/environment";
import {Subscription} from "../../model/Subscription";
import {MessageService} from "../../service/message.service";

// Declare SockJS and Stomp
declare let SockJS: any;
declare let Stomp: any;

@Component({
  selector: 'app-internal-chat',
  templateUrl: './internal-chat.component.html',
  styleUrls: ['./internal-chat.component.scss']
})
export class InternalChatComponent implements OnInit, OnDestroy {
  @Input()
  oneChitChat: Chitchat;
  title: string = 'websocket-frontend';
  input: any;
  url = environment.appApi + '/socket';
  private chitchatId: number;
  public stompClient: any;
  public msg: Message[] = [];

  constructor(private tokenStorageService: TokenStorageService,
              private messageService: MessageService) {
  }

  init(chitchatId: number) {
    this.chitchatId = chitchatId;
    this.messageService.getChitchatAllMessages(this.chitchatId).subscribe(data => {
      this.msg = data.reverse();
      this.initializeWebSocketConnection();
    });
  }

  initializeWebSocketConnection() {
    const serverUrl = this.url;
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    // tslint:disable-next-line:only-arrow-functions
    let headers = {
      'auth-token': this.tokenStorageService.getToken()
    };
    this.stompClient.connect(headers, (frame: any) => {
      that.stompClient.subscribe('/message.' + this.chitchatId.toString(), (message: any) => {
        if (message.body) {
          // @ts-ignore
          that.msg.unshift(JSON.parse(message.body));
        }
      });
    });
  }

  sendMessageStompClient(message: any) {
    this.stompClient.send('/app/send/message/' + this.chitchatId.toString(), {}, JSON.stringify(message));
  }

  ngOnInit(): void {
    this.init(this.oneChitChat.id);
  }

  ngOnDestroy(): void {
    console.log('InternalChatComponent on destroy');
    for (const sub in this.stompClient.subscriptions) {
      if (this.stompClient.subscriptions.hasOwnProperty(sub)) {
        this.stompClient.unsubscribe(sub);
      }
    }
  }

  sendMessage() {
    if (this.input) {
      this.sendMessageStompClient(
          new Message(
              0,
              new Date().toISOString(),
              this.tokenStorageService.getUser(),
              this.oneChitChat.id,
              this.input,
              Subscription.CHAT
          ));
      this.input = '';
    }
  }
}
