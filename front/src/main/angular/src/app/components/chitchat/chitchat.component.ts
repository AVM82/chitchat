import {Component} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {ChitchatService} from "../../service/chitchat.service";
import {OneChitchatComponent} from "../one-chitchat/one-chitchat.component";
import {MatDialog} from "@angular/material/dialog";
import {AddNewChitchatComponent} from "../add-new-chitchat/add-new-chitchat.component";
import {Language} from "../../model/Language";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";

@Component({
  selector: 'app-chitchat',
  templateUrl: './chitchat.component.html',
  styleUrls: ['./chitchat.component.scss']
})
export class ChitchatComponent {
  chitchats: Chitchat[] ;
  oneChitchat: Chitchat | null;
  languages: Language[];
  levels: Level[];
  categories: Category[];

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
        disableClose: true,
        autoFocus: true,
      });
    });

    }

  openAddTaskDialog() {
    this.dialog.open(AddNewChitchatComponent, {
      data: [this.categories,this.levels,this.languages],
      hasBackdrop: true,
      width: "550px",
      disableClose: true,
      autoFocus: true,
    });
  }
}
