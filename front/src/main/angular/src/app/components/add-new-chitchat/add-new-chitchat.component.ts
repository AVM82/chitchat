import { Component } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-add-new-chitchat',
  templateUrl: './add-new-chitchat.component.html',
  styleUrls: ['./add-new-chitchat.component.scss']
})
export class AddNewChitchatComponent {
  constructor(
              private dialog: MatDialog) {
  }

}
