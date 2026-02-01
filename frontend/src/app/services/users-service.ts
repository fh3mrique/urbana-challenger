import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

    constructor(
        private readonly _httpClient: HttpClient
    ) { }


   getUsers(): Observable<any> {
    return this._httpClient.get<any>('http://localhost:8080/api/usuarios');
  }
}