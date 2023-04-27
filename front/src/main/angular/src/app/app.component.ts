import {Component, OnInit} from '@angular/core';
import {Category} from "./model/Category";
import {CategoryService} from "./service/category.service";
import {LoginComponent} from "./auth/login/login.component";
import {MatDialog} from "@angular/material/dialog";
import {RegisterComponent} from "./auth/register/register.component";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  categories: Category[] ;

  constructor(private categoryService: CategoryService,private dialog: MatDialog) {
  }

  ngOnInit() {
    this.categoryService.getAll().subscribe(result=>{
      this.categories = result;
      console.log(result.length);
    });
  }

  login() {
    let dialogRef = this.dialog.open(LoginComponent, {
      data: ['Login in system'],
      hasBackdrop: true,
      width:"40%",
      disableClose: false,
      autoFocus: true,
    });
  }

  signup() {
    let dialogRef = this.dialog.open(RegisterComponent, {
      data: ['Register in system'],
      hasBackdrop: true,
      width:"40%",
      disableClose: false,
      autoFocus: true,
    });
  }

  openChitChat() {
    
  }
}
