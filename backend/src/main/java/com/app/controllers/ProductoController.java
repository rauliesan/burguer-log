package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.ProductoRequest;
import com.app.dtos.ProductoResponse;
import com.app.services.ProductoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
@Validated
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping("/producto")
    public ResponseEntity<ProductoResponse> crearProducto(@RequestBody @Valid ProductoRequest request) {
        return ResponseEntity.ok().body(service.crearProducto(request));
    }

    @GetMapping("/productos/{id_burguer}")
    public ResponseEntity<List<ProductoResponse>> obtenerProductosBurguer(
            @PathVariable(name = "id_burguer") @NotNull Long idBurguer) {
        return ResponseEntity.ok().body(service.obtenerProductosBurguer(idBurguer));
    }

    @PutMapping("/producto/{id}")
    public ResponseEntity<ProductoResponse> editarProducto(@PathVariable @NotNull Long id,
            @RequestBody @Valid ProductoRequest request) {
        return ResponseEntity.ok().body(service.editarProducto(id, request));
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable @NotNull Long id) {
        service.eliminarProducto(id);
        return ResponseEntity.ok().body("Producto eliminado correctamente.");
    }

}
