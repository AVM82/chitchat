import {Component, OnInit} from '@angular/core';
import {UserForEditDto} from "../../model/UserForEditDto";
import {Gender} from "../../model/Gender";
import {Language} from "../../model/Language";
import {LanguageService} from "../../service/language.service";
import {UserForResponseDto} from "../../model/UserForResponseDto";
import {ProfileService} from "../../service/profile.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-profile-user-data',
  templateUrl: './profile-user-data.component.html',
  styleUrls: ['./profile-user-data.component.scss']
})
export class ProfileUserDataComponent implements OnInit {
  userForResponseDto: UserForResponseDto;
  tmpUserName: string;
  tmpRoles: string[];
  tmpEmail: string;
  tmpAvatar: string;
  tmpNativeLanguage: Language | undefined;
  tmpFirstname: string;
  tmpLastname: string;
  tmpGender: Gender;
  tmpDob: string | null;
  tmpRole: string;

  genders: Gender[] = [];
  languages: Language[] = [];
  roles: string[] = ['Practitioner', 'Observer', 'Coach'];
  tmpDate: Date | null;

  ngOnInit(): void {
    this.genders = [Gender.MALE, Gender.FEMALE];
    this.languageService.getAll().subscribe(result => {
      this.languages = result;
    });
    this.profileService.getProfileDetails().subscribe(result => {
      this.userForResponseDto = result;
      this.tmpUserName = this.userForResponseDto.userName;
      this.tmpEmail = this.userForResponseDto.email;
      this.tmpRoles = this.userForResponseDto.roles;
      this.tmpAvatar = this.userForResponseDto.avatar;
      this.tmpNativeLanguage = this.languages.find(
          el => el.languageName === this.userForResponseDto.nativeLanguage);
      this.tmpFirstname = this.userForResponseDto.firstname;
      this.tmpLastname = this.userForResponseDto.lastname;
      this.tmpGender = this.userForResponseDto.gender;
      this.tmpDob = this.userForResponseDto.dob;
      this.tmpDate = new Date(this.tmpDob);
    });
  }

  constructor(private languageService: LanguageService,
              private profileService: ProfileService) {
  }

  changeNativeLanguage() {
    console.log(this.tmpNativeLanguage);
  }

  save() {
    let datePipe = new DatePipe("en-US");
    this.tmpDob = datePipe.transform(this.tmpDate, 'yyyy-MM-dd');
    let newUserForEditDto = new UserForEditDto(this.tmpFirstname, this.tmpLastname,
        this.tmpRole, this.tmpAvatar, this.tmpNativeLanguage?.codeIso || '',
        this.tmpDob || '', this.tmpGender);
    this.profileService.updateUserData(newUserForEditDto).subscribe(data => {
    });
  }
}
