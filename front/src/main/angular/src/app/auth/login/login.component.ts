import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {TokenStorageService} from "../../service/token-storage.service";
import {NotificationService} from "../../service/notification.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public loginForm: FormGroup | any;
  public loginError: boolean = false;
  public passwordError: boolean = false;

  constructor(
      private authService: AuthService,
      private router: Router,
      private dialogRef: MatDialogRef<LoginComponent>,
      private tokenStorage: TokenStorageService,
      private notificationService: NotificationService,
      private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.loginForm = this.createLoginForm();
  }

  createLoginForm(): FormGroup {
    return this.fb.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required])],
    });
  }

  login() {
    if (this.loginForm.value.username.length<3){this.loginError=true}
    if (this.loginForm.value.password.length<6){this.passwordError=true}
    if (!this.loginError && !this.passwordError) {
      this.authService.login({
        username: this.loginForm.value.username,
        password: this.loginForm.value.password
      }).subscribe(data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveRefreshToken(data.refreshToken);
        this.tokenStorage.saveUser();
        this.notificationService.showSnackBar('Successfully logged in');
        this.dialogRef.close(true);
      }, error => {
        this.loginError=true
        this.passwordError=true
        this.notificationService.showSnackBar('Login or password is incorrect!');
      });
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
