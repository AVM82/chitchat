import {Component, Inject, OnInit} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ChitchatService} from "../../service/chitchat.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-one-chitchat',
  templateUrl: './one-chitchat.component.html',
  styleUrls: ['./one-chitchat.component.scss']
})
export class OneChitchatComponent implements OnInit{
  oneChitChat: Chitchat
  oneChitChatSubject = new Subject<Chitchat>();

  constructor(
      private chitchatService: ChitchatService,
      private dialogRef: MatDialogRef<OneChitchatComponent>,
      @Inject(MAT_DIALOG_DATA) private data: [Chitchat],
  ) {
    this.oneChitChatSubject.subscribe((val) => {
      this.oneChitChat = val;
    });
  }

  ngOnInit() {
    this.oneChitChatSubject.next(this.data[0]);
  }

  closeEvent($event: any) {
    this.dialogRef.close();
  }
}
