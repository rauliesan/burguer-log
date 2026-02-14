import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CrearPedido, PedidoResponse } from '../models/pedido.model';

@Injectable({ providedIn: 'root' })
export class PedidoService {

    private readonly API = '/api';

    constructor(private http: HttpClient) { }

    crearPedido(data: CrearPedido): Observable<PedidoResponse> {
        return this.http.post<PedidoResponse>(`${this.API}/pedido`, data);
    }

    cerrarPedido(id: number): Observable<PedidoResponse> {
        return this.http.put<PedidoResponse>(`${this.API}/pedido/${id}/cerrar`, {});
    }

    obtenerPedidos(idBurguer: number): Observable<PedidoResponse[]> {
        return this.http.get<PedidoResponse[]>(`${this.API}/pedidos/${idBurguer}`);
    }
}
