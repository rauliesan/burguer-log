package com.app.entities;

import java.util.Set;

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
@Table(name = "mesa")
public class Mesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Para controlar que el número de la mesa sea único al insertarla se buscará si existe ya una en ese burguer
	@Column(name = "number", nullable = false)
	private Long numero;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mesa")
	private Set<Pedido> pedidos;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "burguer_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_mesa_burguer"))
	private Burguer burguer;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Set<Pedido> getOrders() {
		return pedidos;
	}

	public void setOrders(Set<Pedido> orders) {
		this.pedidos = orders;
	}

	public Burguer getBurguer() {
		return burguer;
	}

	public void setBurguer(Burguer burguer) {
		this.burguer = burguer;
	}
	
}
