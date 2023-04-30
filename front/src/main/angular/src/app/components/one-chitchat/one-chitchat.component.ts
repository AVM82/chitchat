import {Component, Inject} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {TokenStorageService} from "../../service/token-storage.service";
import {ChitchatService} from "../../service/chitchat.service";

@Component({
  selector: 'app-one-chitchat',
  templateUrl: './one-chitchat.component.html',
  styleUrls: ['./one-chitchat.component.scss']
})
export class OneChitchatComponent {
  oneChitChat: Chitchat

  constructor(
      private chitchatService: ChitchatService,
      @Inject(MAT_DIALOG_DATA) private data: [Chitchat],
      private tokenStorageService: TokenStorageService
  ) {  }

  ngOnInit() {
    this.oneChitChat = this.data[0];
  }

  addToChitchat(chitchat: Chitchat) {
    this.chitchatService.addUserInChat(this.tokenStorageService.getUserId(),chitchat.id).subscribe(result => {
      this.oneChitChat = result;
      });
  }
}
