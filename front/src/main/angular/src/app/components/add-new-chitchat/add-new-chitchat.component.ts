import {Component} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Language} from "../../model/Language";
import {Level} from "../../model/Level";
import {Category} from "../../model/Category";
import {CategoryService} from "../../service/category.service";
import {LanguageService} from "../../service/language.service";
import {TokenStorageService} from "../../service/token-storage.service";
import {NewChitChatDTO} from "../../model/NewChitChatDTO";
import {ChitchatService} from "../../service/chitchat.service";
import {DatePipe} from "@angular/common";
import {NotificationService} from "../../service/notification.service";
import {translate} from "@ngneat/transloco";

@Component({
  selector: 'app-add-new-chitchat',
  templateUrl: './add-new-chitchat.component.html',
  styleUrls: ['./add-new-chitchat.component.scss']
})

export class AddNewChitchatComponent {
  newLanguage: Language[];
  newLevel: Level[];
  newCategory: Category[];
  tmpHeader: string ="";
  tmpDescription: string ="";
  tmpCategory: number = 1;
  tmpLanguage: string = 'en';
  tmpDate: Date | null;
  tmpLevel: string = 'A2';
  tmpCapacity: number = 5;
  tmpTime: any = '14:00';


  constructor(
      private categoryService: CategoryService,
      private languageService: LanguageService,
      private chitchatService: ChitchatService,
      private dialogRef: MatDialogRef<AddNewChitchatComponent>,
      private notificationService: NotificationService,
      private tokenStorageService: TokenStorageService,
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


  onCancel() {

  }

  addNewChitchat() {
    let datePipe = new DatePipe("en-US");
    let ChitChatDate = datePipe.transform(this.tmpDate, 'yyyy-MM-dd')+'T'+this.tmpTime;
    let newChitchat = new NewChitChatDTO(this.tmpHeader,this.tmpCategory,
    this.tmpDescription,this.tmpLanguage,this.tmpLevel,this.tmpCapacity,ChitChatDate);
    if(this.tmpDate!=undefined) {
      this.chitchatService.add(newChitchat).subscribe(data => {
      });
      this.notificationService.showSnackBar(translate('confirm message create'));
      this.dialogRef.close();
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
