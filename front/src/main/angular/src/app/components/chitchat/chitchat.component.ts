import {Component, Input} from '@angular/core';
import {Chitchat} from "../../model/Chitchat";
import {ChitchatService} from "../../service/chitchat.service";
import {OneChitchatComponent} from "../one-chitchat/one-chitchat.component";
import {MatDialog} from "@angular/material/dialog";
import {AddNewChitchatComponent} from "../add-new-chitchat/add-new-chitchat.component";
import {Language} from "../../model/Language";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-chitchat',
  templateUrl: './chitchat.component.html',
  styleUrls: ['./chitchat.component.scss']
})
export class ChitchatComponent {
  chitchats: Chitchat[];
  oneChitchat: Chitchat | null;
  languages: Language[];
  levels: Level[];
  categories: Category[];
  @Input()
  selectedCategory: Category | null;
  totalElements: number = 0;
  filteredLanguage: string = "";
  filteredLevel: string = "";
  filteredDateFrom: string = "";
  filteredDateTo: string = "";
  filteredCategory: Category | null;

  constructor(
      private chitchatService: ChitchatService,
      private dialog: MatDialog
  ) {
  }

  ngOnInit() {
    this.chitchats = [];
  }

  openChitChat(chitchat: Chitchat) {
    this.chitchatService.get(chitchat.id).subscribe(result => {
      this.oneChitchat = result;
      this.dialog.open(OneChitchatComponent, {
        data: [this.oneChitchat],
        hasBackdrop: true,
        disableClose: true,
        autoFocus: true,
      });
    });

  }

  openAddTaskDialog() {
    this.dialog.open(AddNewChitchatComponent, {
      data: [this.categories, this.levels, this.languages],
      hasBackdrop: true,
      width: "550px",
      disableClose: true,
      autoFocus: true,
    });
  }

  filter(data: any) {
    this.chitchats = data.content;
    this.totalElements = data.totalElements;

    this.filteredLanguage = data.filteredLanguage;
    this.filteredLevel = data.filteredLevel;
    this.filteredDateFrom = data.filteredDateFrom;
    this.filteredDateTo = data.filteredDateTo;
    this.filteredCategory = data.filteredCategory;
    this.getChitchats(data.page);
  }

  private getChitchats(request: Object) {
    this.chitchatService.filter(
        this.filteredLanguage,
        this.filteredLevel,
        this.filteredDateFrom,
        this.filteredDateTo,
        this.filteredCategory,
        request)
    .subscribe(data => {
      this.chitchats = data['content'];
      this.totalElements = data['totalElements'];
    });
  }

  nextPage(event: PageEvent) {
    const request = {page: "", size: "", sort: ""};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    request['sort'] = 'date';
    this.getChitchats(request);
  }
}
