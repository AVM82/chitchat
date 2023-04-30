import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

const AUTH_API = '/api/v1/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public login(user: { username: any; password: any; }): Observable<any> {
    return this.http.post(AUTH_API + '/authenticate', {
      username: user.username,
      password: user.password
    });
  }

  public register(user: { email: any; username: any; password: any; }): Observable<any> {
    return this.http.post(AUTH_API + '/register', {
      email: user.email,
      username: user.username,
      password: user.password
    });
  }
}
