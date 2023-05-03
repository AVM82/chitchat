import {Component} from '@angular/core';
import {Chitchat} from "./model/Chitchat";
import {Subscription} from "rxjs";
import {ChitchatService} from "./service/chitchat.service";
import {TokenStorageService} from "./service/token-storage.service";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  chitchatId: any = null;
  oneChitChat: Chitchat;
  private querySubscription: Subscription;

  constructor(
      private chitchatService: ChitchatService,
      private tokenStorageService: TokenStorageService,
      private route: ActivatedRoute
  ) {
    this.querySubscription = route.queryParams.subscribe(
        (queryParam: any) => {
          if (queryParam['id']!=undefined) {
          this.chitchatId = queryParam['id'];
          console.log(this.chitchatId);
          this.chitchatService.get(this.chitchatId)
          .subscribe(value => this.oneChitChat = value);
        }
        }
    );
  }

}
