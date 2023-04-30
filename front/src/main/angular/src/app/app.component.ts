import {Component, OnInit} from '@angular/core';
import {LoginComponent} from "./auth/login/login.component";
import {MatDialog} from "@angular/material/dialog";
import {RegisterComponent} from "./auth/register/register.component";
import {LanguageService} from "./service/language.service";
import {Language} from "./model/Language";
import {Level} from "./model/Level";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  languages: Language[];
  levels: Level[]

  constructor(private dialog: MatDialog,
              private languageService: LanguageService
  ) {
  }

  ngOnInit() {
    this.languageService.getAll().subscribe(result=>{
      this.languages = result;
    });
    this.languageService.getLevels().subscribe(result=>{
      this.levels = result;
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
}
