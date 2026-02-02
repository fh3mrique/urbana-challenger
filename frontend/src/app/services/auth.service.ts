import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Credencials } from "../types/login/credencials.interface";
import { API_CONFIG } from "../utils/requestConfig";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private readonly _http: HttpClient) { }

    authenticate(creds: Credencials) {
        return this._http.post(`${API_CONFIG.baseUrl}/auth/login`, creds, {
            observe: 'response',
            responseType: 'text'
        })
    }

    successfulLogin(authToken: string) {
        localStorage.setItem('token', authToken);
    }
}