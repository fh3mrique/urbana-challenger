import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../utils/requestConfig';

export type TipoCartao = 'COMUM' | 'ESTUDANTE' | 'TRABALHADOR';

export interface AddCardRequest {
  numeroCartao: number;
  tipoCartao: TipoCartao;
}

export interface UpdateCardStatusRequest {
  status: boolean;
}

export interface ICard {
  id: number;
  numeroCartao: number;
  tipoCartao: TipoCartao;
  status: boolean;
}

@Injectable({ providedIn: 'root' })
export class CardService {
  private readonly baseUrl = `${API_CONFIG.baseUrl}/usuarios`;

  constructor(private readonly http: HttpClient) {}

  findAllByUser(userId: number): Observable<ICard[]> {
    return this.http.get<ICard[]>(`${this.baseUrl}/${userId}/cartoes`);
  }

  addToUser(userId: number, payload: AddCardRequest): Observable<ICard> {
    return this.http.post<ICard>(`${this.baseUrl}/${userId}/cartoes`, payload);
  }

  updateStatus(userId: number, cartaoId: number, status: boolean): Observable<ICard> {
    const payload: UpdateCardStatusRequest = { status };
    return this.http.patch<ICard>(`${this.baseUrl}/${userId}/cartoes/${cartaoId}/status`, payload);
  }
}
