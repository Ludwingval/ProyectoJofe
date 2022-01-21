package com.orders.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

@Entity
public class Listado extends Pedido {

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "lista_productos")
	@MapKeyColumn(name = "producto")
	@Column(name = "cant")	    
	private Map<Producto, String> productos = new HashMap<>();

	public Map<Producto, String> getProductos() {
		return productos;
	}

	public void setProductos(Map<Producto, String> productos) {
		this.productos = productos;
	}

	
}
