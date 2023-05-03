import {Component, OnInit} from '@angular/core';
import {Language} from "../../model/Language";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";
import {MatDialog} from "@angular/material/dialog";
import {LanguageService} from "../../service/language.service";
import {LoginComponent} from "../../auth/login/login.component";
import {RegisterComponent} from "../../auth/register/register.component";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  languages: Language[];
  levels: Level[];
  selectedCategory: Category | null;

  constructor(private dialog: MatDialog,
              private languageService: LanguageService
  ) {
  }

  ngOnInit() {

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

  selectCategory(category: Category | null) {
    this.selectedCategory = category;
  }
}
