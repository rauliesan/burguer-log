package com.app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductoRequest {

    @NotBlank
    private String nombre;

    @NotNull
    @Positive
    private Double precio;

    @NotNull
    private Long idBurguer;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getIdBurguer() {
        return idBurguer;
    }

    public void setIdBurguer(Long idBurguer) {
        this.idBurguer = idBurguer;
    }

}
