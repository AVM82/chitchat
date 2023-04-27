import {Component, OnInit} from '@angular/core';
import {Category} from "../../model/Category";
import {CategoryService} from "../../service/category.service";
import {MatDialog} from "@angular/material/dialog";
import {LoginComponent} from "../../auth/login/login.component";
import {RegisterComponent} from "../../auth/register/register.component";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  categories: Category[] ;

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit() {
    this.categoryService.getAll().subscribe(result=>{
      this.categories = result;
    });
  }

  openChitChat() {

  }
}
