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
    if (this.registerForm.value.password == this.registerForm.value.confirmPassword) {
      this.authService.register({
        username: this.registerForm.value.username,
        email: this.registerForm.value.email,
        password: this.registerForm.value.password
      }).subscribe(data => {
        this.notificationService.showSnackBar(translate('Successfully registered!'));
        this.dialogRef.close();
      }, error => {
        this.notificationService.showSnackBar('Some data errors during registration');
      });
    }
  }
}
