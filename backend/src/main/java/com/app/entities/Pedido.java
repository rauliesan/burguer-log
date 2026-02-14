package com.app.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Fecha en la que empiezan los pedidos de esa mesa
	@Column(name = "startDate", nullable = false)
	private LocalDateTime startDate;
	
	// Fecha en la que se termina en esa mesa y se le da el ticket, se cierra para que esa mesa esté libre
	@Column(name = "endDate")
	private LocalDateTime endDate;
	
	@Column(name = "finalizado", nullable = false)
	private Boolean finalizado;
	
	@Column(name = "precio_final")
	private Double precioFinal;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "mesa_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_pedido_mesa"))
	private Mesa mesa;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineaPedido> lineas = new HashSet<>();
	
	
	public void addLinea(LineaPedido linea) {
	    lineas.add(linea);
	    linea.setPedido(this);
	}
	
	public void cerrarPedido() {

	    if (Boolean.TRUE.equals(this.finalizado)) {
	        throw new IllegalStateException("El pedido ya está cerrado");
	    }

	    this.precioFinal = calcularTotal();
	    this.endDate = LocalDateTime.now();
	    this.finalizado = true;
	}
	
	private Double calcularTotal() {
	    return lineas.stream()
	            .mapToDouble(l -> l.getCantidad() * l.getPrecioUnitario())
	            .sum();
	}



	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(Double precioFinal) {
		this.precioFinal = precioFinal;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Set<LineaPedido> getLineas() {
		return lineas;
	}

	public void setLineas(Set<LineaPedido> lineas) {
		this.lineas = lineas;
	}
}
