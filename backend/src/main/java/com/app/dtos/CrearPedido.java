package com.app.dtos;

import java.util.Set;

public class CrearPedido {
	
	private Long mesaId;
    private Set<LineaPedidoDTO> lineas;

    public Long getMesaId() {
        return mesaId;
    }

    public void setMesaId(Long mesaId) {
        this.mesaId = mesaId;
    }

    public Set<LineaPedidoDTO> getLineas() {
        return lineas;
    }

    public void setLineas(Set<LineaPedidoDTO> lineas) {
        this.lineas = lineas;
    }
}
