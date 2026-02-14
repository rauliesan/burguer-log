export interface ProductoRequest {
    nombre: string;
    precio: number;
    idBurguer: number;
}

export interface ProductoResponse {
    id: number;
    nombre: string;
    precio: number;
    activo: boolean;
}
