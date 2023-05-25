import {Injectable} from '@angular/core';

const TOKEN_KEY = 'auth-chitchat-token';
const USER_KEY = 'auth-chitchat-user';
const REFRESH_TOKEN_KEY = 'auth-chitchat-refresh-token';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() {
  }

  public saveToken(token: string): void {
    console.log(token)
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public saveRefreshToken(refreshToken: string): void {
    window.localStorage.removeItem(REFRESH_TOKEN_KEY);
    window.localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
  }

  public getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  public getRefreshToken(): string | null {
    return localStorage.getItem(REFRESH_TOKEN_KEY);
  }

  public tokenExpired(token: any) {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }

  public saveUser() {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, this.getUser());
  }

  public getUser(): any {
    let token = localStorage.getItem(TOKEN_KEY);
    if (token != null) {
      let decodedJWT = JSON.parse(window.atob(token.split('.')[1]));
      return decodedJWT.user_name;
    }
  }

  public getUserId(): any {
    let token = localStorage.getItem(TOKEN_KEY);
    if (token != null) {
      let decodedJWT = JSON.parse(window.atob(token.split('.')[1]));
      console.log('user_id: ' + decodedJWT.user_id);
      return decodedJWT.user_id;
    }
  }

  logOut(): void {
    window.localStorage.clear();
    window.location.reload();
  }
}
