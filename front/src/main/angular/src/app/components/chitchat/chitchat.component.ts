import { Component } from '@angular/core';
import {Category} from "../../model/Category";
import {CategoryService} from "../../service/category.service";
import {Chitchat} from "../../model/Chitchat";
import {ChitchatService} from "../../service/chitchat.service";

@Component({
  selector: 'app-chitchat',
  templateUrl: './chitchat.component.html',
  styleUrls: ['./chitchat.component.scss']
})
export class ChitchatComponent {
  chitchats: Chitchat[] ;

  constructor(private chitchatService: ChitchatService) {
  }

  ngOnInit() {
    this.chitchatService.getAll().subscribe(result=>{
      this.chitchats = result;
    });
  }

  openChitChat() {

  }
}
