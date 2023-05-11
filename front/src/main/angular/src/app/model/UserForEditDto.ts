import {Gender} from "./Gender";

export class UserForEditDto {
  firstname: string;
  lastname: string;
  role: string;
  avatar: string;
  nativeLanguage: string;
  dob: string;
  gender: Gender;

  constructor(firstname: string, lastname: string, role: string, avatar: string,
              nativeLanguage: string, dob: string, gender: Gender) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.role = role;
    this.avatar = avatar;
    this.nativeLanguage = nativeLanguage;
    this.dob = dob;
    this.gender = gender;
  }
}