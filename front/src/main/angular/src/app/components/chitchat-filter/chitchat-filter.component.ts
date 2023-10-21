import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Language} from "../../model/Language";
import {LanguageService} from "../../service/language.service";
import {ChitchatService} from "../../service/chitchat.service";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";
import {CategoryService} from "../../service/category.service";

@Component({
  selector: 'app-chitchat-filter',
  templateUrl: './chitchat-filter.component.html',
  styleUrls: ['./chitchat-filter.component.scss']
})
export class ChitchatFilterComponent implements OnInit {
  filteredLanguage: string = "";
  filteredLevel: string = "";
  filteredDateFrom: string = new Date().toISOString().split('T')[0];
  filteredDateTo: string = new Date(new Date().setMonth(new Date().getMonth() + 24)).toISOString().split('T')[0];
  languages: Language[] = [];
  categories: Category[] ;
  levels: Level[] = [];
  data: any;
  @Output()
  chitchatsEvent = new EventEmitter<any>();
  filteredCategory: Category | null;
  private page = {page: "0", size: "6", sort: "date"};

  constructor(private languageService: LanguageService
              , private chitchatService: ChitchatService
  ,private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.languageService.getAll().subscribe(result => {
      this.languages = result;
    });
    this.languageService.getLevels().subscribe(result => {
      this.levels = result;
    });
    this.categoryService.getAll().subscribe(result=>{
      this.categories = result;
    });
  }

  @Input()
  set selectedCategory(category: Category | null) {
    this.filteredCategory = category;
    this.getFilter();
  }

  getFilter() {
    this.chitchatService.filter(
        this.filteredLanguage,
        this.filteredLevel,
        this.filteredDateFrom,
        this.filteredDateTo,
        this.filteredCategory,
        this.page)
    .subscribe({
      next: (data: any) => {
        this.data = data;
        this.chitchatsEvent.emit({
          data: this.data,
          filteredLanguage: this.filteredLanguage,
          filteredLevel: this.filteredLevel,
          filteredDateFrom: this.filteredDateFrom,
          filteredDateTo: this.filteredDateTo,
          filteredCategory: this.filteredCategory,
          page: this.page
        })
      }
    })
  }
}
