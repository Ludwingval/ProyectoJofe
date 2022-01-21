package com.orders.services;



import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.entities.Listado;
import com.orders.entities.Pedido;
import com.orders.entities.Producto;
import com.orders.entities.Usuario;
import com.orders.errors.ErrorService;
import com.orders.repos.PedidoRepo;
import com.orders.repos.ProductoRepo;
import com.orders.repos.UsuarioRepo;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepo pedidoRepo;
	
	@Autowired
	private ProductoRepo productoRepo;
	
	@Autowired
	private UsuarioRepo usuarioRepo;

	
	@Transactional
	public void agregar(Map<String, String> params) throws ErrorService {
	
		Pedido pedido = new Pedido();
		Listado listado = new Listado();
		
		
		for (Map.Entry<String, String> entry : params.entrySet()) {		
		    System.out.println(entry.getKey() + ":" + entry.getValue());
		   
		    String key = entry.getKey();
		    String value =  entry.getValue();
		    
		    
		    
		    
		    
		     /*AGREGO EL USUARIO AL PEDIDO*/
		    if (key.equalsIgnoreCase("user")) {
				Optional<Usuario> respuestaUsuario = usuarioRepo.findById(value);
				
				if (respuestaUsuario.isPresent()) {		
					pedido.setUsuario(usuarioRepo.getById(value));
				}
			}
		    

		    if (key.matches("[+-]?\\d*(\\.\\d+)?")) {
				if (productoRepo.findById((long) Integer.parseInt(key)).isPresent() && Integer.parseInt(value) != 0 ) {
			    	
					Optional <Producto> prod = productoRepo.findById((long) Integer.parseInt(key));

					/*productos.put(prod.get(), value);*/

					
				}
			}	    
		}

		pedido.setListado(listado);
		pedido.setFechaPedido(new Date());
		
		/*

		pedido.setTotal(total);
		*/
		pedidoRepo.save(pedido);
		
	}
	
	
	@Transactional
	public void modificar(Usuario usuario, Listado listado, Date fechaEntrega, String total, String id) throws ErrorService {
		
		Optional<Pedido> respuesta = pedidoRepo.findById(id);
		
		if	(respuesta.isPresent()) {
			Pedido pedido = respuesta.get();
			pedido.setUsuario(usuario);
			pedido.setListado(listado);
			pedido.setFechaPedido(new Date());
			pedido.setFechaEntrega(fechaEntrega);
			pedido.setTotal(total);
			
			pedidoRepo.save(pedido);	
			
		} else {
			throw new ErrorService("No se encontró el pedido solicitado.");
		}
		
	}
	
	/*	
	@Transactional
	public HashMap<String, String> listadoProductos() throws ErrorService {
		
		HashMap <String, String> map = new HashMap<>();
		
	
		String[] productos = null;
		for (Pedido pedido : pedidoRepo.findAll()) {		
			productos = pedido.getProductos().split("-");
		}
		for (String producto : productos) {				
			String[] value = producto.split("cant");
			map.put(value[0], value[1]);
		}
		
		map.remove(null);

		return map;
	}
*/	
	

	@Transactional
	public void entregado(String id) throws ErrorService {
		
		Optional<Pedido> respuesta = pedidoRepo.findById(id);
		
		if	(respuesta.isPresent()) {
			Pedido pedido = respuesta.get();
			pedido.setFechaEntrega(new Date());		
			pedidoRepo.save(pedido);	
			
		} else {
			throw new ErrorService("No se encontró el pedido solicitado.");
		}		
	}
	
	
	
	public List<Producto> buscarPorUsuario(String nombre) {
		return pedidoRepo.listarPorUsuario(nombre + "%");
	}



	
	


}
