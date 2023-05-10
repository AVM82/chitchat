import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  url = environment.appApi + '/api/v1';

  constructor(private httpClient: HttpClient) {
  }

  uploadAvatar(formData: FormData): Observable<any> {
    return this.httpClient.post(this.url + '/profile/avatar', formData);
  }

}
