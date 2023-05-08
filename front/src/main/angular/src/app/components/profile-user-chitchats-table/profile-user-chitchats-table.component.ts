import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatSort, Sort} from "@angular/material/sort";
import {Chitchat} from "../../model/Chitchat";
import {ProfileService} from "../../service/profile.service";

@Component({
  selector: 'app-profile-user-chitchats-table',
  templateUrl: './profile-user-chitchats-table.component.html',
  styleUrls: ['./profile-user-chitchats-table.component.scss']
})
export class ProfileUserChitchatsTableComponent implements OnInit, AfterViewInit {
  chitchats: Chitchat[];
  displayedColumns: string[] = ['id', 'chatName', 'authorName', 'categoryName',
    'description', 'languageName', 'level', 'capacity', 'date', 'usersInChitchat'];
  dataSource: MatTableDataSource<Chitchat>;
  @Input()
  chitchatsType: string;

  constructor(private _liveAnnouncer: LiveAnnouncer,
              private profileService: ProfileService) {
  }

  @ViewChild(MatSort) sort: MatSort;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  /** Announce the change in sort state for assistive technology. */
  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  ngOnInit(): void {
    if (this.chitchatsType === 'my_chitchats') {
      this.profileService.getUserCreatedChats().subscribe(result => {
        this.chitchats = result;
        this.dataSource = new MatTableDataSource(this.chitchats);
      });
    } else if (this.chitchatsType === 'planned_chitchats') {
      this.profileService.getChatsWithUser().subscribe(result => {
        this.chitchats = result;
        this.dataSource = new MatTableDataSource(this.chitchats);
      });
    } else if (this.chitchatsType === 'archive_chitchats') {
      this.profileService.getArchiveChats().subscribe(result => {
        this.chitchats = result;
        this.dataSource = new MatTableDataSource(this.chitchats);
      });
    }
  }
}
