import {Component, OnInit} from '@angular/core';
import {Language} from "../../model/Language";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";
import {MatDialog} from "@angular/material/dialog";
import {LanguageService} from "../../service/language.service";
import {LoginComponent} from "../../auth/login/login.component";
import {RegisterComponent} from "../../auth/register/register.component";
import { TranslocoService } from '@ngneat/transloco';
import {TokenStorageService} from "../../service/token-storage.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  languages: Language[];
  levels: Level[];
  selectedCategory: Category | null;
  selectedLanguage: string = 'en';
  isLoggedIn: boolean =  false;
  currentUser: string='';


  constructor(private dialog: MatDialog,
              private languageService: LanguageService,
              private translocoService: TranslocoService,
              private tokenStorageService: TokenStorageService
  ) {
  }

  ngOnInit() {
    this.checkUserLogged();
    this.languageService.getAll().subscribe(result => {
      this.languages = result;
    });
  }

  login() {
    let dialogRef = this.dialog.open(LoginComponent, {
      data: ['Login in system'],
      hasBackdrop: true,
      width: "40%",
      disableClose: false,
      autoFocus: true,
    });
    dialogRef.afterClosed().subscribe(result => {
      this.isLoggedIn = result;
      this.currentUser = this.tokenStorageService.getUser();
    });
  }


  checkUserLogged(){
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn){
      console.log("logged");
      if (this.tokenStorageService.tokenExpired(this.tokenStorageService.getToken())) {
        console.log("non valid");
      }else {
        console.log("valid");
        this.currentUser = this.tokenStorageService.getUser();
        console.log(this.currentUser)
      }

    }

  }

  signup() {
    let dialogRef = this.dialog.open(RegisterComponent, {
      data: ['Register in system'],
      hasBackdrop: true,
      width: "40%",
      disableClose: false,
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
}
