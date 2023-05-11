import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
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
      username: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.compose([Validators.required])],
    });
  }

  login() {
    this.authService.login({
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    }).subscribe(data => {
      this.tokenStorage.saveToken(data.token);
      this.tokenStorage.saveUser(data);
      this.notificationService.showSnackBar('Successfully logged in');
      this.dialogRef.close(true);
    }, error => {
      this.notificationService.showSnackBar('Error data for login');
      this.dialogRef.close(false);
    });
  }
}
