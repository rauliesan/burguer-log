import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductoRequest, ProductoResponse } from '../models/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoService {

    private readonly API = '/api';

    constructor(private http: HttpClient) { }

    crearProducto(data: ProductoRequest): Observable<ProductoResponse> {
        return this.http.post<ProductoResponse>(`${this.API}/producto`, data);
    }

    obtenerProductos(idBurguer: number): Observable<ProductoResponse[]> {
        return this.http.get<ProductoResponse[]>(`${this.API}/productos/${idBurguer}`);
    }

    editarProducto(id: number, data: ProductoRequest): Observable<ProductoResponse> {
        return this.http.put<ProductoResponse>(`${this.API}/producto/${id}`, data);
    }

    eliminarProducto(id: number): Observable<string> {
        return this.http.delete(`${this.API}/producto/${id}`, { responseType: 'text' });
    }
}
