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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.orders.errors.ErrorService;
import com.orders.repos.PedidoRepo;

import com.orders.services.PedidoService;



@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping("/orders")
public class OrderController {
	

	@Autowired
	private PedidoRepo pedidoRepo;

	@Autowired 
	private PedidoService pedidoService;
	
	@GetMapping("")
	public String orders(Model model) throws ErrorService {	
		

			 model.addAttribute("orders", pedidoRepo.findAll());

	   
		return "orders.html";
	}
	

	@PostMapping("")
	public String save(ModelMap modelo, @RequestParam String id, RedirectAttributes redirect) {
		
		try {
			pedidoService.entregado(id);
			
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		    return "orders.html";
		}
		redirect.addFlashAttribute("success", "Se ha registrado correctamente.");
		return "redirect:/orders";
	}

	
	

	  

	
}