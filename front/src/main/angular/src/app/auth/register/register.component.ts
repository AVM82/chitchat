import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {NotificationService} from "../../service/notification.service";
import {MatDialogRef} from "@angular/material/dialog";
import {translate} from "@ngneat/transloco";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  public registerForm: FormGroup | any;
  public loginError: boolean = false;
  public isLoading: boolean = false;
  public emailError: boolean = false;
  public passwordEqualsError: boolean = false;

  constructor(
      private authService: AuthService,
      private router: Router,
      private dialogRef: MatDialogRef<RegisterComponent>,
      private notificationService: NotificationService,
      private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.registerForm = this.createRegisterForm();
  }

  createRegisterForm(): FormGroup {
    return this.fb.group({
      email: ['', Validators.compose([Validators.required, Validators.email])],
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required])],
      confirmPassword: ['', Validators.compose([Validators.required])]
    });
  }

  register() {

    this.loginError = false;
    this.emailError = false;
    this.passwordEqualsError = false;
    if (this.registerForm.value.username.length < 3) {
      this.loginError = true
    }
    if (!this.registerForm.value.email.includes('@') ||
        !this.registerForm.value.email.includes(".")) {
      this.emailError = true
    }
    if (this.registerForm.value.password.length < 5 ||
        this.registerForm.value.password != this.registerForm.value.confirmPassword) {
      this.passwordEqualsError = true
    }
    if (!this.loginError && !this.emailError && !this.passwordEqualsError) {
      if (this.registerForm.value.password == this.registerForm.value.confirmPassword) {
        this.isLoading = true;
        this.authService.register({
          username: this.registerForm.value.username,
          email: this.registerForm.value.email,
          password: this.registerForm.value.password
        }).subscribe(data => {
          this.notificationService.showSnackBar(translate('Successfully registered!'),'succes');
          this.isLoading = false;
          this.dialogRef.close();
        }, error => {
          this.isLoading = false;
          this.notificationService.showSnackBar('Some data errors during registration','error');
        });
      }
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
