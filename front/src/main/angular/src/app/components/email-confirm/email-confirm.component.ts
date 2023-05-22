import {Component} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {TokenStorageService} from "../../service/token-storage.service";
import {NotificationService} from "../../service/notification.service";

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
      private router: Router,
      private route: ActivatedRoute,
      private tokenStorage: TokenStorageService,
      private notificationService:NotificationService
  ) {
    this.querySubscription = route.queryParams.subscribe(
        (queryParam: any) => {
          this.clickToken = queryParam['click'];
        }
    );
  }

  newUserEmailConfirm() {
    this.authService.newUserEmailConfirm(this.clickToken)
    .subscribe(value => {
      this.clickToken = value
      this.tokenStorage.saveToken(value.token);
      // this.tokenStorage.saveRefreshToken(data.refreshToken);
      this.tokenStorage.saveUser();
      let currentUserId = this.tokenStorage.getUserId();
      this.router.navigate(['/profile']);
    });

    console.log("***** "+this.clickToken)
  }
}
