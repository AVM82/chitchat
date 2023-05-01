import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Category} from "../../model/Category";
import {CategoryService} from "../../service/category.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  categories: Category[] ;
  @Output()
  selectCategoryEvent = new EventEmitter<Category>();

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit() {
    this.categoryService.getAll().subscribe(result=>{
      this.categories = result;
    });
  }

  openChitChat() {

  }

  selectCategory(category: Category) {
    this.selectCategoryEvent.emit(category);
  }
}
