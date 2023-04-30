import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Category} from "../model/Category";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  url = '/api/v1/category';

  constructor(private httpClient: HttpClient) {}

  add(obj: Category): Observable<Category> {
    return  this.httpClient.post<Category>(this.url,obj);
  }

  get(id: number): Observable<Category> {
    return this.httpClient.get<Category>(this.url+'/'+id);
  }

  getAll(): Observable<Category[]> {
    const headers = new HttpHeaders({ "Access-Control-Allow-Origin": "*" });
    return this.httpClient.get<Category[]>(this.url+"/all",{
      headers: headers
    });
  }

  update(obj: Category): Observable<Category> {
    return this.httpClient.put<Category>(this.url+'/',obj);
  }

}
