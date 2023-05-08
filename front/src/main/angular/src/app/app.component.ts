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
  click: any = null ;
  profile: any = null ;

  constructor(
      private chitchatService: ChitchatService,
      private tokenStorageService: TokenStorageService,
      private route: ActivatedRoute
  ) {
    this.querySubscription = route.queryParams.subscribe(
        (queryParam: any) => {
          this.chitchatId = queryParam['id'];
          this.click = queryParam['click'];
          this.profile = queryParam['profile'];
        }
    );
  }

}
