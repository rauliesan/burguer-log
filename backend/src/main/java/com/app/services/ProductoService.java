package com.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.app.dtos.ProductoRequest;
import com.app.dtos.ProductoResponse;
import com.app.entities.Burguer;
import com.app.entities.Producto;
import com.app.exceptions.BurguerNotFoundException;
import com.app.exceptions.EntidadNotCreatedException;
import com.app.exceptions.EntidadNotDeletedException;
import com.app.exceptions.EntidadNotFoundException;
import com.app.repositories.BurguerRepository;
import com.app.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private BurguerRepository burguerRepository;

    public ProductoResponse crearProducto(ProductoRequest request) {
        Optional<Burguer> optionalBurguer = burguerRepository.findById(request.getIdBurguer());
        if (optionalBurguer.isEmpty()) {
            throw new BurguerNotFoundException(request.getIdBurguer());
        }

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setActivo(true);
        producto.setBurguer(optionalBurguer.get());

        try {
            productoRepository.save(producto);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadNotCreatedException("Producto");
        }

        return mapperEntityToResponse(producto);
    }

    public List<ProductoResponse> obtenerProductosBurguer(Long idBurguer) {
        Optional<Burguer> optionalBurguer = burguerRepository.findById(idBurguer);
        if (optionalBurguer.isEmpty()) {
            throw new BurguerNotFoundException(idBurguer);
        }
        List<Producto> productos = productoRepository.findByBurguerId(idBurguer);
        List<ProductoResponse> responses = new ArrayList<>();
        for (Producto producto : productos) {
            responses.add(mapperEntityToResponse(producto));
        }
        return responses;
    }

    public ProductoResponse editarProducto(Long id, ProductoRequest request) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isEmpty()) {
            throw new EntidadNotFoundException("Producto");
        }
        Producto producto = optionalProducto.get();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());

        try {
            productoRepository.save(producto);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadNotCreatedException("Producto");
        }

        return mapperEntityToResponse(producto);
    }

    public void eliminarProducto(Long id) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isEmpty()) {
            throw new EntidadNotFoundException("Producto");
        }
        try {
            productoRepository.delete(optionalProducto.get());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadNotDeletedException("Producto", id);
        }
    }

    private ProductoResponse mapperEntityToResponse(Producto producto) {
        ProductoResponse response = new ProductoResponse();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setPrecio(producto.getPrecio());
        response.setActivo(producto.getActivo());
        return response;
    }

}
