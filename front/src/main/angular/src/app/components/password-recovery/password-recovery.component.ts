import {Component} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.scss']
})
export class PasswordRecoveryComponent {
  clickToken: any;
  private querySubscription: Subscription;
  newPassword: string;
  newPasswordConfirmation: string;

  constructor(
      private authService: AuthService,
      private route: ActivatedRoute,
      private notificationService: NotificationService
  ) {
    this.querySubscription = route.queryParams.subscribe(
        (queryParam: any) => {
          this.clickToken = queryParam['click'];
          console.log(this.clickToken);
        }
    );
  }

  passwordRecoveryEmailConfirm() {
    if (this.newPassword.length > 0 && this.newPassword === this.newPasswordConfirmation) {
      this.authService.passwordRecoveryEmailConfirm(this.clickToken, this.newPassword)
      .subscribe(value => {
        this.clickToken = value;
        this.notificationService.showSnackBar("The password was changed!",'succes');
        this.newPassword = '';
        this.newPasswordConfirmation = '';
      });
    } else {
      this.notificationService.showSnackBar("The entered passwords do not match!",'error');
    }
  }
}
