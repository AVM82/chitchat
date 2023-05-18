import {Component} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-email-confirm',
  templateUrl: './email-confirm.component.html',
  styleUrls: ['./email-confirm.component.scss']
})
export class EmailConfirmComponent {
  clickToken:any;
  private querySubscription: Subscription;

  constructor(
      private authService: AuthService,
      private route: ActivatedRoute
  ) {
    this.querySubscription = route.queryParams.subscribe(
        (queryParam: any) => {
          this.clickToken = queryParam['click'];
          console.log(this.clickToken);
        }
    );
  }

  newUserEmailConfirm() {
    this.authService.newUserEmailConfirm(this.clickToken)
    .subscribe(value => {
      this.clickToken = value
    console.log(value)
    });
  }
}
