import {Component, Inject, NgModule, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {Language} from "../../model/Language";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";
import {Chitchat} from "../../model/Chitchat";
import {CategoryService} from "../../service/category.service";
import {LanguageService} from "../../service/language.service";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

@Component({
  selector: 'app-add-new-chitchat',
  templateUrl: './add-new-chitchat.component.html',
  styleUrls: ['./add-new-chitchat.component.scss']
})

export class AddNewChitchatComponent {
  newLanguage: Language[];
  newLevel: Level[];
  newCategory: Category[];
  tmpHeader: string;
  tmpDescription: string;
  tmpCategory: Category;
  tmpLanguage: Language;
  tmpDate: Date;
  tmpLevel: Level;


  constructor(
              private categoryService: CategoryService,
              private languageService: LanguageService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.categoryService.getAll().subscribe(result => {
      this.newCategory = result;
    });
    this.languageService.getAll().subscribe(result => {
      this.newLanguage = result;
    });
    this.languageService.getLevels().subscribe(result => {
      this.newLevel = result;
    });
  }

  addChitchat() {

  }

  onConfirm() {

  }

  onCancel() {

  }

  addNewChitchat() {
    console.log(this.tmpCategory.name)
    console.log(this.tmpDescription)
    console.log(this.tmpLanguage.languageName)
    console.log(this.tmpLevel)
    console.log(this.tmpDate.getDate())
  }
}
