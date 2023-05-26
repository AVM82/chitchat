import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatSort, Sort} from "@angular/material/sort";
import {Chitchat} from "../../model/Chitchat";
import {ProfileService} from "../../service/profile.service";
import {MatPaginator} from "@angular/material/paginator";
import {MessageService} from "../../service/message.service";
import {ChitchatUnreadCount} from "../../model/ChitchatUnreadCount";
import {OneChitchatComponent} from "../one-chitchat/one-chitchat.component";
import {ChitchatService} from "../../service/chitchat.service";
import {MatDialog} from "@angular/material/dialog";
import { Subject } from 'rxjs';

@Component({
  selector: 'app-profile-user-chitchats-table',
  templateUrl: './profile-user-chitchats-table.component.html',
  styleUrls: ['./profile-user-chitchats-table.component.scss']
})
export class ProfileUserChitchatsTableComponent implements OnInit, AfterViewInit {
  chitchats: Chitchat[] = [];
  displayedColumns: string[] = ['id', 'chatName', 'authorName', 'categoryName',
    'description', 'languageName', 'level', 'capacity', 'date', 'usersInChitchat'];
  dataSource: MatTableDataSource<Chitchat> = new MatTableDataSource(this.chitchats);
  @Input()
  chitchatsType: string;
  onlyUnreadFilter: boolean;
  @Input()
  onlyUnreadFilterSubject: Subject<boolean>;
  private unreadChitchats: ChitchatUnreadCount[] = [];

  constructor(private _liveAnnouncer: LiveAnnouncer,
              private profileService: ProfileService,
              private messageService: MessageService,
              private chitchatService: ChitchatService,
              private dialog: MatDialog,
  ) {
  }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.setDataSource(this.chitchats);
    this.onlyUnreadFilterSubject.subscribe((val) => {
      // console.log(this.onlyUnreadFilter, val);
      this.onlyUnreadFilter = val;
      this.onlyUnreadFilterChange();
    });
  }

  /** Announce the change in sort state for assistive technology. */
  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
      // console.log(`${sortState}`, `${sortState.active}`, `Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
      // console.log(`${sortState}`, `${sortState.active}`, 'Sorting cleared');
    }
  }

  ngOnInit(): void {
    this.messageService.getAllUnreadUserChitchats().subscribe(result => {
      this.unreadChitchats = result;
      if (this.chitchatsType === 'my_chitchats') {
        this.profileService.getUserCreatedChats().subscribe(result => {
          this.setDataSource(result);
        });
      } else if (this.chitchatsType === 'planned_chitchats') {
        this.profileService.getChatsWithUser().subscribe(result => {
          this.setDataSource(result);
        });
      } else if (this.chitchatsType === 'archive_chitchats') {
        this.profileService.getArchiveChats().subscribe(result => {
          this.setDataSource(result);
        });
      }
    });
  }

  private setDataSource(result: Chitchat[]) {
    result.forEach(value => value.countUnreadMessages = this.countUnreadMessages(value.id))
    this.chitchats = result;
    this.dataSource = new MatTableDataSource(this.chitchats);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.dataSource.filterPredicate =
        (data, filter) => data.countUnreadMessages.toString() !== filter;
    this.onlyUnreadFilterChange();
  }

  countUnreadMessages(chitchatId: number): number {
    return this.unreadChitchats.filter(value => value.chitchatId === chitchatId)
    .reduce((prev, current) => prev + current.unreadCount, 0);
  }

  openChitchat(chitchatId: number) {
    // TODO add route
    //this.router.navigate(['/one_chitchat', { id: chitchatId }]);
    this.chitchatService.get(chitchatId).subscribe(result => {
      let oneChitchat = result;
      this.dialog.open(OneChitchatComponent, {
        data: [oneChitchat],
        hasBackdrop: true,
        disableClose: true,
        autoFocus: true,
      }).afterClosed().subscribe(value => {
        this.messageService.getAllUnreadUserChitchats().subscribe(result => {
          let unreadCount = result.filter(value => value.chitchatId === chitchatId).reduce(
              (prev, current) => prev + current.unreadCount, 0);
          this.dataSource.data.filter(value1 => value1.id===chitchatId).forEach(
              value1 => value1.countUnreadMessages = unreadCount);
          this.onlyUnreadFilterChange();
        })
      })
    });
  }

  onlyUnreadFilterChange() {
    this.dataSource.filter = this.onlyUnreadFilter ? '0' : '';
  }
}
