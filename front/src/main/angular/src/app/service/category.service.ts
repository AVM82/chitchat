import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Category} from "../model/Category";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  url = 'http://localhost:5000/api/v1/category';

  constructor(private httpClient: HttpClient) {}

  add(obj: Category): Observable<Category> {
    return  this.httpClient.post<Category>(this.url+'/',obj);
  }

  get(id: number): Observable<Category> {
    return this.httpClient.get<Category>(this.url+'/'+id);
  }

  getAll(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.url+'/');
  }

  update(obj: Category): Observable<Category> {
    return this.httpClient.put<Category>(this.url+'/',obj);
  }

}
