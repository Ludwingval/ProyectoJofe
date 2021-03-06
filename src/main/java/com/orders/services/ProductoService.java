package com.orders.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.entities.Producto;
import com.orders.errors.ErrorService;
import com.orders.repos.ProductoRepo;



@Service
public class ProductoService {
	
	
	
	
	@Autowired
	private ProductoRepo productoRepo;
	


	
	@Transactional
	public void agregar(String nombre, String marca, String precio, String color, String descrip) throws ErrorService {
		
		validar(nombre, marca, precio, descrip);
		
		Producto producto = new Producto();
		
		producto.setNombre(nombre);
		producto.setMarca(marca);
		producto.setPrecio(precio);
		producto.setColor(color);
		producto.setDescrip(descrip);
		
		productoRepo.save(producto);
		
	}
	
	
	@Transactional
	public void modificar(String nombre, String marca, String precio, String color, String descrip, Long id) throws ErrorService {

		validar(nombre, marca, precio, descrip);
		
		Optional<Producto> respuesta = productoRepo.findById(id);
		
		if	(respuesta.isPresent()) {
			Producto producto = respuesta.get();
			producto.setNombre(nombre);
			producto.setMarca(marca);
			producto.setPrecio(precio);
			producto.setColor(color);
			producto.setDescrip(descrip);
			
			productoRepo.save(producto);	
			
		} else {
			throw new ErrorService("No se encontrĂ³ el producto solicitado.");
		}
		
	}
	
	
	
	
	private void validar(String nombre, String marca, String precio, String descrip) throws ErrorService {
		
		System.out.println("EntrĂ³ a validarse.");
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorService("El nombre del producto no puede ser nulo.");
		}
		if (marca == null || marca.isEmpty()) {
			throw new ErrorService("La marca del producto no puede ser nula.");
		}
		if (precio == null ||precio.isEmpty()) {
			throw new ErrorService("El precio del producto no puede ser nulo.");
		}

		if (descrip == null || descrip.isEmpty()) {
			throw new ErrorService("La descripciĂ³n del producto no puede ser nula..");
		}
		
		System.out.println("TerminĂ³ de validarse.");
	}
	
	
	public List<Producto> buscarPorNombre(String nombre) {
		return productoRepo.listarPorNombre(nombre + "%");
	}


	public List<Producto> buscarPorDescrip(String descrip) {
		return productoRepo.listarPorDescrip(descrip + "%");
	}

	
	



	
	


}
