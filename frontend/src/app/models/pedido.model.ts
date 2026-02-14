export interface LineaPedidoDTO {
    productoId: number;
    cantidad: number;
}

export interface CrearPedido {
    mesaId: number;
    lineas: LineaPedidoDTO[];
}

export interface PedidoResponse {
    id: number;
    numeroMesa: number;
    fechaInicio: string;
    finalizado: boolean;
    precioFinal: number | null;
}
