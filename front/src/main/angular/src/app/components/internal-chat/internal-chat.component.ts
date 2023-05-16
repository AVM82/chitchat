import {Component, Input} from '@angular/core';
import {MessageService} from "../../service/message.service";
import {Chitchat} from "../../model/Chitchat";
import {TokenStorageService} from "../../service/token-storage.service";

@Component({
  selector: 'app-internal-chat',
  templateUrl: './internal-chat.component.html',
  styleUrls: ['./internal-chat.component.scss']
})
export class InternalChatComponent {
  @Input()
  oneChitChat: Chitchat;
  title: string = 'websocket-frontend';
  input: any;

  constructor(public messageService: MessageService,
              private tokenStorageService: TokenStorageService) {
  }

  sendMessage() {
    if (this.input) {
      this.messageService.sendMessage(
          this.tokenStorageService.getUser() + '##' +
          this.oneChitChat.id + '##' +
          this.input
      );
      this.input = '';
      console.log(this.messageService.msg);
    }
  }
}
