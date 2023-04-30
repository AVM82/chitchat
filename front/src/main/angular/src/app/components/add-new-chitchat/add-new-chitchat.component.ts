import { Component } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {Language} from "../../model/Language";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";

@Component({
  selector: 'app-add-new-chitchat',
  templateUrl: './add-new-chitchat.component.html',
  styleUrls: ['./add-new-chitchat.component.scss']
})
export class AddNewChitchatComponent {
  languages: Language[];
  levels: Level[];
  categories: Category[];

  constructor(
              private dialog: MatDialog) {
  }

}
