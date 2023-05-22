import { Component } from '@angular/core';
import {Subject} from "rxjs";

@Component({
  selector: 'app-profile-user-chitchats',
  templateUrl: './profile-user-chitchats.component.html',
  styleUrls: ['./profile-user-chitchats.component.scss']
})
export class ProfileUserChitchatsComponent {
  onlyUnreadFilter: boolean = false;
  onlyUnreadFilterSubject = new Subject<boolean>();


  constructor() {
    this.onlyUnreadFilterSubject.subscribe((val) => {
      this.onlyUnreadFilter = val;
    });
  }

  changeOnlyUnreadFilter() {
    this.onlyUnreadFilterSubject.next(this.onlyUnreadFilter);
  }
}
