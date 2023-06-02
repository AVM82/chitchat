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
import {TokenStorageService} from "../../service/token-storage.service";
import {NotificationService} from "../../service/notification.service";

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
  isLoggedIn: boolean = false;
  totalElements: number = 0;
  filteredLanguage: string = "";
  filteredLevel: string = "";
  filteredDateFrom: string = "";
  filteredDateTo: string = "";
  filteredCategory: Category | null;

  constructor(
      private chitchatService: ChitchatService,
      private notificationService: NotificationService,
      private tokenStorageService: TokenStorageService,
      private dialog: MatDialog
  ) {
  }

  ngOnInit() {
    this.chitchats = [];
  }

  openChitChat(chitchat: Chitchat) {
    this.checkUserLogged();
    if(this.isLoggedIn) {
      this.chitchatService.get(chitchat.id).subscribe(result => {
        this.oneChitchat = result;
        this.dialog.open(OneChitchatComponent, {
          data: [this.oneChitchat],
          hasBackdrop: true,
          width: "65%",
          disableClose: true,
          autoFocus: true,
        });
      });
    } else {
      this.notificationService.showSnackBar("Please log in before!")
    }

  }

  openAddTaskDialog() {
    this.checkUserLogged();
    if (this.isLoggedIn) {
      this.dialog.open(AddNewChitchatComponent, {
        data: [this.categories, this.levels, this.languages],
        hasBackdrop: true,
        width: "550px",
        disableClose: true,
        autoFocus: true,
      });
    } else{
      this.notificationService.showSnackBar("Please log in before!")
    }
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

  checkUserLogged() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      if (this.tokenStorageService.tokenExpired(this.tokenStorageService.getToken())) {
        this.isLoggedIn = false;
      }
    }
  }
}
