import {Component} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {ChitchatService} from "../../service/chitchat.service";
import {TokenStorageService} from "../../service/token-storage.service";
import {ActivatedRoute} from "@angular/router";
import {Subject, Subscription} from "rxjs";

@Component({
  selector: 'app-chitchat-ref',
  templateUrl: './chitchat-ref.component.html',
  styleUrls: ['./chitchat-ref.component.scss']
})
export class ChitchatRefComponent {
  oneChitChat: Chitchat;
  chitchatId: any;
  private querySubscription: Subscription;
  oneChitChatSubject = new Subject<Chitchat>();

  constructor(
      private chitchatService: ChitchatService,
      private tokenStorageService: TokenStorageService,
      private route: ActivatedRoute
  ) {
    this.oneChitChatSubject.subscribe((val) => {
      this.oneChitChat = val;
    });
    this.querySubscription = route.queryParams.subscribe(
        (queryParam: any) => {
          this.chitchatId = queryParam['id'];
          console.log(this.chitchatId);
          this.chitchatService.getPublic(this.chitchatId).subscribe(value => {
            this.oneChitChat = value;
            this.oneChitChatSubject.next(this.oneChitChat);
          });
        }
    );
  }
}
