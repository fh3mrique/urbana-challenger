import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../utils/requestConfig';
import { Observable } from 'rxjs';
import { IUser } from '../interfaces/user.interface';
import { CreateUserRequest } from '../interfaces/create-user-request.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private readonly _http: HttpClient) { }

  findById(id: number): Observable<any> {
  return this._http.get<any>(`${API_CONFIG.baseUrl}/api/usuarios/${id}`);
}

  findAll(): Observable<IUser[]> {
    return this._http.get<IUser[]>('http://localhost:8080/api/usuarios')
  }

  create(payload: CreateUserRequest): Observable<IUser> {
  return this._http.post<IUser>(`${API_CONFIG.baseUrl}/api/usuarios`, payload);
}

  update(id: number, payload: { nome: string; email: string; senha: string; id?: number }) {
  return this._http.put(`${API_CONFIG.baseUrl}/api/usuarios/${id}`, payload);
}

  deleteUser(userId: number): Observable<void> {
    return this._http.delete<void>(`${API_CONFIG.baseUrl}/api/usuarios/${userId}`);
  }

  

}
