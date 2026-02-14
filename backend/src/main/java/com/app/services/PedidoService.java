package com.app.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.app.dtos.CrearPedido;
import com.app.dtos.LineaPedidoDTO;
import com.app.dtos.PedidoResponse;
import com.app.entities.LineaPedido;
import com.app.entities.Mesa;
import com.app.entities.Pedido;
import com.app.entities.Producto;
import com.app.exceptions.EntidadNotCreatedException;
import com.app.exceptions.EntidadNotFoundException;
import com.app.exceptions.MesaOcupadaException;
import com.app.repositories.MesaRepository;
import com.app.repositories.PedidoRepository;
import com.app.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public PedidoResponse crearPedido(CrearPedido request) {

        // 1️ Comprobar que la mesa existe
        Optional<Mesa> optionalMesa = mesaRepository.findById(request.getMesaId());
        if (optionalMesa.isEmpty()) {
            throw new EntidadNotFoundException("Mesa");
        }

        Mesa mesa = optionalMesa.get();

        // 2️ Comprobar que no tenga pedido abierto
        Optional<Pedido> pedidoAbierto = pedidoRepository.findByMesaIdAndFinalizadoFalse(mesa.getId());

        if (pedidoAbierto.isPresent()) {
            throw new MesaOcupadaException(mesa.getNumero());
        }

        // 3️ Crear pedido
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setStartDate(LocalDateTime.now());
        pedido.setFinalizado(false);

        // 4️ Añadir líneas
        for (LineaPedidoDTO lineaDTO : request.getLineas()) {

            Optional<Producto> optionalProducto = productoRepository.findById(lineaDTO.getProductoId());

            if (optionalProducto.isEmpty()) {
                throw new EntidadNotFoundException("Producto");
            }

            Producto producto = optionalProducto.get();

            LineaPedido linea = new LineaPedido();
            linea.setProducto(producto);
            linea.setCantidad(lineaDTO.getCantidad());
            linea.setPrecioUnitario(producto.getPrecio());

            pedido.addLinea(linea);
        }

        try {
            pedidoRepository.save(pedido);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadNotCreatedException("Pedido");
        }

        return mapperEntityToPedidoResponse(pedido);
    }

    public PedidoResponse cerrarPedido(Long idPedido) {

        Optional<Pedido> optionalPedido = pedidoRepository.findById(idPedido);

        if (optionalPedido.isEmpty()) {
            throw new EntidadNotFoundException("Pedido");
        }

        Pedido pedido = optionalPedido.get();

        pedido.cerrarPedido();

        return mapperEntityToPedidoResponse(pedido);
    }

    public java.util.List<PedidoResponse> obtenerPedidosBurguer(Long idBurguer) {
        java.util.List<com.app.entities.Pedido> pedidos = pedidoRepository.findByMesaBurguerId(idBurguer);
        java.util.List<PedidoResponse> responses = new java.util.ArrayList<>();
        for (Pedido pedido : pedidos) {
            responses.add(mapperEntityToPedidoResponse(pedido));
        }
        return responses;
    }

    // =========================
    // ======= MAPPERS =========
    // =========================

    private PedidoResponse mapperEntityToPedidoResponse(Pedido pedido) {

        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setNumeroMesa(pedido.getMesa().getNumero());
        response.setFechaInicio(pedido.getStartDate());
        response.setFinalizado(pedido.getFinalizado());

        if (Boolean.TRUE.equals(pedido.getFinalizado())) {
            response.setPrecioFinal(pedido.getPrecioFinal());
        }

        return response;
    }
}
