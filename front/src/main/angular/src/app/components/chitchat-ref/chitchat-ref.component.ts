import {Component} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {ChitchatService} from "../../service/chitchat.service";
import {TokenStorageService} from "../../service/token-storage.service";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-chitchat-ref',
  templateUrl: './chitchat-ref.component.html',
  styleUrls: ['./chitchat-ref.component.scss']
})
export class ChitchatRefComponent {
  oneChitChat: Chitchat;
  chitchatId: any;
  private querySubscription: Subscription;

  constructor(
      private chitchatService: ChitchatService,
      private tokenStorageService: TokenStorageService,
      private route: ActivatedRoute
  ) {
    this.querySubscription = route.queryParams.subscribe(
        (queryParam: any) => {
          this.chitchatId = queryParam['id'];
          console.log(this.chitchatId);
          this.chitchatService.get(this.chitchatId).subscribe(value => this.oneChitChat = value);
        }
    );
  }

  addToChitchat(chitchat: Chitchat) {
    this.chitchatService.addUserInChat(this.tokenStorageService.getUserId(), chitchat.id).subscribe(result => {
      this.oneChitChat = result;
    });
  }

}
