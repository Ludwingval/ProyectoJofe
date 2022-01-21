package com.orders.control;




import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.orders.config.AuthenticationSystem;

import com.orders.errors.ErrorService;
import com.orders.repos.ProductoRepo;
import com.orders.security.service.UserDetailsServiceImpl;
import com.orders.services.PedidoService;


@Controller
public class InicioController {
	
	@Autowired
	UserDetailsServiceImpl detailsServiceImpl;

	@Autowired
	ProductoRepo productoRepo;
	
	@Autowired
	PedidoService pedidoService;
	
	@GetMapping("/")
	public String bienvenida(@RequestParam(required=false) String error, @RequestParam(required=false) String logout, ModelMap model) {
		
		if (AuthenticationSystem.isLogged()) {	
			return portal(error, model);
		}

		
		if(error != null) {
			model.put("error", "Mail o contraseña incorrecta.");
		}
		if (logout != null ) {
			model.put("logout", "Ha cerrado la sesión correctamente.");
		}
		
		return "index.html";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/portal")
	public String portal(@RequestParam(required=false) String error, ModelMap model) {
		
	
		
		if (AuthenticationSystem.isLogged()) {	
			model.addAttribute("products", productoRepo.findAll());
		}
		return "portal.html";
	}
	
	
	
	
	
	@PostMapping("/portal/neworder")
	public String save(ModelMap modelo, @RequestParam Map<String, String> params) throws ErrorService {


		pedidoService.agregar(params);
	
		modelo.addAttribute("success", "El pedido se realizó correctamente.");
		
		return "portal";
	}
	

}

