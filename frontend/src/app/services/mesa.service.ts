import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MesaRequest, MesaResponse } from '../models/mesa.model';

@Injectable({ providedIn: 'root' })
export class MesaService {

    private readonly API = '/api';

    constructor(private http: HttpClient) { }

    crearMesa(data: MesaRequest): Observable<MesaResponse> {
        return this.http.post<MesaResponse>(`${this.API}/mesa`, data);
    }

    obtenerMesas(idBurguer: number): Observable<MesaResponse[]> {
        return this.http.get<MesaResponse[]>(`${this.API}/mesas/${idBurguer}`);
    }

    eliminarMesa(idBurguer: number, numero: number): Observable<string> {
        return this.http.delete(`${this.API}/mesa/${idBurguer}/${numero}`, { responseType: 'text' });
    }
}
