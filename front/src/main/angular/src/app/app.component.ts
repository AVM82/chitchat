import {Component, OnInit} from '@angular/core';
import {Category} from "./model/Category";
import {CategoryService} from "./service/category.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  categories: Category[] ;

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit() {
    this.categoryService.getAll().subscribe(result=>{
      this.categories = result;
      console.log(result.length);
    });

  }
}
