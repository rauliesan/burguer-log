import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { BurguerLogin, BurguerRequest, BurguerResponse } from '../models/burguer.model';

@Injectable({ providedIn: 'root' })
export class AuthService {

    private readonly API = '/api';

    constructor(private http: HttpClient) { }

    login(data: BurguerLogin): Observable<BurguerResponse> {
        return this.http.post<BurguerResponse>(`${this.API}/burguer/login`, data).pipe(
            tap(res => this.saveSession(res))
        );
    }

    register(data: BurguerRequest): Observable<BurguerResponse> {
        return this.http.post<BurguerResponse>(`${this.API}/burguer/register`, data).pipe(
            tap(res => this.saveSession(res))
        );
    }

    private saveSession(burguer: BurguerResponse): void {
        localStorage.setItem('burguer', JSON.stringify(burguer));
    }

    getBurguer(): BurguerResponse | null {
        const data = localStorage.getItem('burguer');
        return data ? JSON.parse(data) : null;
    }

    getBurguerId(): number | null {
        const burguer = this.getBurguer();
        return burguer ? burguer.id : null;
    }

    isLoggedIn(): boolean {
        return !!localStorage.getItem('burguer');
    }

    logout(): void {
        localStorage.removeItem('burguer');
    }
}
