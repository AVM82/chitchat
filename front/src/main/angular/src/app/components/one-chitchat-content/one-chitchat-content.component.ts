import {Component, EventEmitter, HostListener, Input, Output} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {TokenStorageService} from "../../service/token-storage.service";
import {ChitchatService} from "../../service/chitchat.service";
import {Clipboard} from '@angular/cdk/clipboard';
import {MessageService} from "../../service/message.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-one-chitchat-content',
  templateUrl: './one-chitchat-content.component.html',
  styleUrls: ['./one-chitchat-content.component.scss']
})
export class OneChitchatContentComponent{
  @Input() oneChitChat: Chitchat
  isAuthor: boolean = false;
  currentUser:string
  tmpConferenceLink: string;
  @Input() oneChitChatSubject: Subject<Chitchat>;
  @Output() closeEvent = new EventEmitter<any>();

  constructor(
      private chitchatService: ChitchatService,
      private clipboard: Clipboard,
      private tokenStorageService: TokenStorageService,
      private messageService: MessageService,
  ) {  }

  ngOnInit() {
    this.currentUser = this.tokenStorageService.getUser();
    if (this.oneChitChat) {
      this.isAuthor = this.tokenStorageService.getUser() === this.oneChitChat.authorName;
      this.tmpConferenceLink = this.oneChitChat.conferenceLink;
    }
  }

  ngAfterViewInit() {
    this.oneChitChatSubject.subscribe((val) => {
      // console.log(this.oneChitChat, val);
      this.oneChitChat = val;
      this.isAuthor = this.tokenStorageService.getUser() === this.oneChitChat.authorName;
      this.tmpConferenceLink = this.oneChitChat.conferenceLink;
    });
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event: any) {
    this.markAsReadUserMessagesOfChitchat();
  }

  addToChitchat(chitchat: Chitchat) {
    this.chitchatService.addUserInChat(this.tokenStorageService.getUserId(),chitchat.id).subscribe(result => {
      this.oneChitChat = result;
    });
  }

  markAsReadUserMessagesOfChitchat() {
    this.messageService.putMarkAsReadUserMessagesOfChitchat(this.oneChitChat.id).subscribe();
    this.closeEvent.emit();
  }

  addConferenceLink() {
    this.chitchatService.addChitchatLink(this.oneChitChat, this.tmpConferenceLink).subscribe();
  }

  copyDataToClipboard(avatarUrl: string) {
    this.clipboard.copy(avatarUrl);
  }
}
