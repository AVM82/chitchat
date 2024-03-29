import {Component, OnInit} from '@angular/core';
import {Language} from "../../model/Language";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";
import {MatDialog} from "@angular/material/dialog";
import {LanguageService} from "../../service/language.service";
import {LoginComponent} from "../../auth/login/login.component";
import {RegisterComponent} from "../../auth/register/register.component";
import {TranslocoService} from '@ngneat/transloco';
import {TokenStorageService} from "../../service/token-storage.service";
import {ProfileService} from "../../service/profile.service";
import {Router} from "@angular/router";
import {MessageService} from "../../service/message.service";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  languages: Language[];
  levels: Level[];
  avatarUrl: string;
  selectedCategory: Category | null;
  selectedLanguage: string;
  isLoggedIn: boolean = false;
  currentUser: string = '';
  totalCountUnreadUserMessages: any;


  constructor(private dialog: MatDialog,
              private languageService: LanguageService,
              private translocoService: TranslocoService,
              private profileService: ProfileService,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private messageService: MessageService,
              private notificationService: NotificationService,
  ) {
  }

  ngOnInit() {
    this.selectedLanguage = this.translocoService.getActiveLang();
    this.checkUserLogged();
    this.languageService.getAll().subscribe(result => {
      this.languages = result;
    });
    if (this.isLoggedIn) {
      this.refreshForm();
    }
  }

  private refreshForm() {
    this.profileService.getAvatarUrl().subscribe(
        data => {
          this.avatarUrl = data.url
        });
    this.messageService.getTotalCountUnreadUserMessages().subscribe(
        data => {
          this.totalCountUnreadUserMessages = data.value;
          if (this.totalCountUnreadUserMessages > 0) {
            this.notificationService.showSnackBar('You have unread chat messages.','info')
          }
        });
    this.selectedLanguage = this.translocoService.getActiveLang();
  }

  login() {
    let dialogRef = this.dialog.open(LoginComponent, {
      data: ['Login in system'],
      hasBackdrop: true,
      width: "95%",
      maxWidth: "550px",
      disableClose: true,
      autoFocus: true,
    });
    dialogRef.afterClosed().subscribe(result => {
      this.isLoggedIn = result;
      this.currentUser = this.tokenStorageService.getUser();
      this.refreshForm();
    });
  }


  checkUserLogged() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      if (this.tokenStorageService.tokenExpired(this.tokenStorageService.getToken())) {
      } else {
        this.currentUser = this.tokenStorageService.getUser();
      }
    }
  }

  signup() {
    let dialogRef = this.dialog.open(RegisterComponent, {
      data: ['Register in system'],
      hasBackdrop: true,
      width: "95%",
      maxWidth: "550px",
      disableClose: true,
      autoFocus: true,
    });
  }

  selectCategory(category: Category | null) {
    this.selectedCategory = category;
  }

  changeLanguage(lang: string) {
    this.translocoService.setActiveLang(lang);
  }

  logOut() {
    this.tokenStorageService.logOut();
    this.isLoggedIn = false;
  }

  openUserProfile() {
    let currentUserId = this.tokenStorageService.getUserId();
    this.router.navigate(['/profile']);
  }
}
