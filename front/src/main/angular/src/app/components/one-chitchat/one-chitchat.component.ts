import {Component, Inject} from '@angular/core';
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
export class OneChitchatComponent {
  oneChitChat: Chitchat
  currentUser:string

  constructor(
      private chitchatService: ChitchatService,
      private notificationService: NotificationService,
      private dialogRef: MatDialogRef<OneChitchatComponent>,
      @Inject(MAT_DIALOG_DATA) private data: [Chitchat],
      private tokenStorageService: TokenStorageService,
      private messageService: MessageService,
  ) {  }

  ngOnInit() {
    this.oneChitChat = this.data[0];
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
}
