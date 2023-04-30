import {Component} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {ChitchatService} from "../../service/chitchat.service";
import {OneChitchatComponent} from "../one-chitchat/one-chitchat.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-chitchat',
  templateUrl: './chitchat.component.html',
  styleUrls: ['./chitchat.component.scss']
})
export class ChitchatComponent {
  chitchats: Chitchat[] ;
  oneChitchat: Chitchat | null;

  constructor(
      private chitchatService: ChitchatService,
      private dialog: MatDialog
      ) {  }

  ngOnInit() {
    this.chitchatService.getAll().subscribe(result=>{
      this.chitchats = result;
    });
  }

  openChitChat(chitchat: Chitchat) {
    this.chitchatService.get(chitchat.id).subscribe(result => {
      this.oneChitchat = result;
      this.dialog.open(OneChitchatComponent, {
        data: [this.oneChitchat],
        hasBackdrop: true,
        disableClose: false,
        autoFocus: true,
      });
    });

    }

  openAddTaskDialog() {

  }
}
