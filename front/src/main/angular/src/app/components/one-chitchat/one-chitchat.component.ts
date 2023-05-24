import {Component, Inject, OnInit} from '@angular/core';
import {Clipboard} from '@angular/cdk/clipboard';
import {Chitchat} from "../../model/Chitchat";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TokenStorageService} from "../../service/token-storage.service";
import {ChitchatService} from "../../service/chitchat.service";
import {MessageService} from "../../service/message.service";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-one-chitchat',
  templateUrl: './one-chitchat.component.html',
  styleUrls: ['./one-chitchat.component.scss']
})
export class OneChitchatComponent implements OnInit{
  oneChitChat: Chitchat
  currentUser:string
  isAuthor: boolean = false;
  tmpConferenceLink: string;

  constructor(
      private chitchatService: ChitchatService,
      private clipboard: Clipboard,
      private notificationService: NotificationService,
      private dialogRef: MatDialogRef<OneChitchatComponent>,
      @Inject(MAT_DIALOG_DATA) private data: [Chitchat],
      private tokenStorageService: TokenStorageService,
      private messageService: MessageService,
  ) {  }

  ngOnInit() {
    this.oneChitChat = this.data[0];
    this.isAuthor = this.tokenStorageService.getUser() === this.oneChitChat.authorName;
    this.tmpConferenceLink = this.oneChitChat.conferenceLink;
    this.currentUser = this.tokenStorageService.getUser();
  }

  addToChitchat(chitchat: Chitchat) {
    if (chitchat.authorName!=this.tokenStorageService.getUser()) {
      this.chitchatService.addUserInChat(this.tokenStorageService.getUserId(), chitchat.id).subscribe(result => {
        this.oneChitChat = result;
      });
    }else {
      this.notificationService.showSnackBar("Duplication error!")
    }
  }

  markAsReadUserMessagesOfChitchat() {
    this.messageService.putMarkAsReadUserMessagesOfChitchat(this.oneChitChat.id).subscribe();
    this.dialogRef.close();
  }

  addConferenceLink() {
    this.chitchatService.addChitchatLink(this.oneChitChat, this.tmpConferenceLink).subscribe();
  }

  copyDataToClipboard(avatarUrl: string) {
    this.clipboard.copy(avatarUrl);
  }
}
