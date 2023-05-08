import {Gender} from "./Gender";

export class UserForResponseDto {
  userName: string;
  roles: string[];
  email: string;
  avatar: string;
  nativeLanguage: string;
  firstname: string;
  lastname: string;
  gender: Gender;
  dob: string;

  constructor(userName: string, roles: string[], email: string, avatar: string,
              nativeLanguage: string, firstname: string, lastname: string,
              gender: Gender, dob: string) {
    this.userName = userName;
    this.roles = roles;
    this.email = email;
    this.avatar = avatar;
    this.nativeLanguage = nativeLanguage;
    this.firstname = firstname;
    this.lastname = lastname;
    this.gender = gender;
    this.dob = dob;
  }
}