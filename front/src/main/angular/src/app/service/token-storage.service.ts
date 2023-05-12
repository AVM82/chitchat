import {Injectable} from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const REFRESH_TOKEN_KEY = 'auth-refresh-token';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() {
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public saveRefreshToken(refreshToken: string): void {
    window.sessionStorage.removeItem(REFRESH_TOKEN_KEY);
    window.sessionStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
  }

  public getToken(): string | null {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public tokenExpired(token: any) {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }

  public saveUser() {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, this.getUser());
  }

  public getUser(): any {
    let token = sessionStorage.getItem(TOKEN_KEY);
    if (token != null) {
      let decodedJWT = JSON.parse(window.atob(token.split('.')[1]));
      return decodedJWT.user_name;
    }
  }

  public getUserId(): any {
    let token = sessionStorage.getItem(TOKEN_KEY);
    if (token != null) {
      let decodedJWT = JSON.parse(window.atob(token.split('.')[1]));
      console.log('user_id: ' + decodedJWT.user_id);
      return decodedJWT.user_id;
    }
  }

  logOut(): void {
    window.sessionStorage.clear();
    window.location.reload();
  }
}
