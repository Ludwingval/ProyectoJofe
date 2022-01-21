package com.orders.control;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.orders.repos.ProductoRepo;

import com.orders.services.ProductoService;

@Controller

@PreAuthorize("isAuthenticated()")
@RequestMapping("/products")
public class ProductController {
	
	
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ProductoRepo productoRepo;
	
	@GetMapping("")
	public String products(Model model) {
	    model.addAttribute("products", productoRepo.findAll());
    
		return "products.html";
	}
	


	
	
	@PostMapping("/save")
	public String save(ModelMap modelo, @RequestParam String nombre, @RequestParam String marca, @RequestParam String precio, @RequestParam String color, @RequestParam String descrip) {

		try {
			productoService.agregar(nombre, marca, precio, color, descrip);
			
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		    return "products.html";
		}
		modelo.addAttribute("success", "El producto se ha agregado correctamente.");
		return "products";
	}
	

	

	
	
}