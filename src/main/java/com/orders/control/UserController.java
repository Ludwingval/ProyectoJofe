package com.orders.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.orders.entities.Usuario;
import com.orders.repos.UsuarioRepo;
import com.orders.services.UsuarioService;



@Controller

@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping("/users")
public class UserController {
	
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	
	@GetMapping("")
	public String registro(Model model) {
	    model.addAttribute("users", usuarioRepo.findAll());
    
		return "register.html";
	}
	

	@GetMapping("/search")
	public String buscar(ModelMap modelo, @RequestParam(required = false) String lastname) {
		
		List<Usuario> usuarios = new ArrayList<>();
			if	(lastname != null) {
				usuarios = usuarioService.buscarPorApellido(lastname);
			}
			
			modelo.put("usuarios", usuarios);
		return "usuarios.html";
	}
	
	
	@PostMapping("/save")
	public String save(ModelMap modelo, @RequestParam String name, @RequestParam String lastname, @RequestParam String mail, @RequestParam String area, @RequestParam String password) {
		
		try {
			usuarioService.registrar(name, lastname, mail, area, password);
			
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		    return "register.html";
		}
		modelo.addAttribute("success", "Se ha registrado correctamente.");
		return "register.html";
	}
	

	

	
	
}