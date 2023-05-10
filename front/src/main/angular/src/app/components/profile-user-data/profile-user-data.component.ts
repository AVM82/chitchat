import {Component, OnInit} from '@angular/core';
import {UserForEditDto} from "../../model/UserForEditDto";
import {Gender} from "../../model/Gender";
import {Language} from "../../model/Language";
import {LanguageService} from "../../service/language.service";
import {UserForResponseDto} from "../../model/UserForResponseDto";
import {ProfileService} from "../../service/profile.service";
import {FileUploadService} from "../../service/file-upload.service";

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
  tmpDob: string;
  tmpRole: string;

  genders: Gender[] = [];
  languages: Language[] = [];
  roles: string[] = ['Practitioner', 'Observer', 'Coach'];
  fileName: string = '';
  requiredFileType: string[] = ['image/png','image/jpeg'];
  MAX_AVATAR_SIZE: number = 200 * 1024;

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
    });
  }

  constructor(private languageService: LanguageService,
              private profileService: ProfileService,
              private fileUploadService: FileUploadService) {
  }

  changeNativeLanguage() {
    console.log(this.tmpNativeLanguage);
  }

  save() {
    let newUserForEditDto = new UserForEditDto(this.tmpFirstname, this.tmpLastname,
        this.tmpRole, this.tmpAvatar, this.tmpNativeLanguage?.codeIso || '',
        this.tmpDob, this.tmpGender);
    this.profileService.updateUserData(newUserForEditDto).subscribe(data => {
    });
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file && this.checkAvatarFile(file)) {
      this.fileName = file.name;
      const formData = new FormData();
      formData.append("thumbnail", file);
      const upload$ = this.fileUploadService.uploadAvatar(formData);
      upload$.subscribe(result => {
        this.tmpAvatar = result;
      });
    }
  }

  private checkAvatarFile(file: File) {
    console.log(file.type.toLowerCase());
    return file.size <= this.MAX_AVATAR_SIZE
        && (file.type.toLowerCase() === "image/png" ||
            file.type.toLowerCase() === "image/jpeg");
  }
}
