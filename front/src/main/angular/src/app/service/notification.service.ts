import {Injectable} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackbar: MatSnackBar) { }

  public showSnackBar(message: string,status:string | "info"): void {
    this.snackbar.open(message, undefined, {
      duration: 2000,
      panelClass:[status],
      verticalPosition: 'top',
      horizontalPosition: 'right'
    });
  }
}

