import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment";

const AUTH_API = environment.appApi + '/api/v1/auth';

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

  public newUserEmailConfirm(clickToken: string): Observable<any> {
   return this.http.post(AUTH_API + '/click', {
     username:'',
     password:clickToken
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
