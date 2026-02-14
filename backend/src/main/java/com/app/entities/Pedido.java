package com.app.entities;

import java.time.LocalDateTime;

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
	@Column(name = "endDate", nullable = false)
	private LocalDateTime endDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "mesa_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_pedido_mesa"))
	private Mesa mesa;
	
}
