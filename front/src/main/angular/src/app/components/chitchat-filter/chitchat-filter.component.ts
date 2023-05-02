import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Language} from "../../model/Language";
import {LanguageService} from "../../service/language.service";
import {ChitchatService} from "../../service/chitchat.service";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";

@Component({
  selector: 'app-chitchat-filter',
  templateUrl: './chitchat-filter.component.html',
  styleUrls: ['./chitchat-filter.component.scss']
})
export class ChitchatFilterComponent implements OnInit {
  filteredLanguage: string = "";
  filteredLevel: string = "";
  filteredDateFrom: string = "";
  filteredDateTo: string = "";
  languages: Language[] = [];
  levels: Level[] = [];
  chitchats = [];
  @Output()
  chitchatsEvent = new EventEmitter<any>();
  filteredCategory: Category;

  constructor(private languageService: LanguageService, private chitchatService: ChitchatService) {
  }

  ngOnInit(): void {
    this.languageService.getAll().subscribe(result => {
      this.languages = result;
    });
    this.languageService.getLevels().subscribe(result => {
      this.levels = result;
    });
  }

  @Input()
  set selectedCategory(category: Category) {
    this.filteredCategory = category;
    this.getFilter();
  }

  getFilter() {
    this.chitchatService.filter(
        this.filteredLanguage,
        this.filteredLevel,
        this.filteredDateFrom,
        this.filteredDateTo,
        this.filteredCategory)
    .subscribe({
      next: (data: any) => {
        this.chitchats = data;
        this.chitchatsEvent.emit(this.chitchats);
      }
    })
  }
}
