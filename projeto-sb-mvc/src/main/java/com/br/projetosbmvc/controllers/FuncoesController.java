package com.br.projetosbmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Role;
import com.br.projetosbmvc.model.Usuario;
import com.br.projetosbmvc.repository.RoleRepository;
import com.br.projetosbmvc.repository.UsuarioRepository;

@Controller
@RequestMapping("/funcoes")
public class FuncoesController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping("/{idUser}")
	public ModelAndView init (@PathVariable(name = "idUser") Long idUser) {
		ModelAndView modelAndView = new ModelAndView("pages/funcoesuser");
		Usuario usuario = usuarioRepository.findById(idUser).get();
		modelAndView.addObject("usuarioObj", usuario);
		modelAndView.addObject("rolesUsuario", usuario.getRoles());
		modelAndView.addObject("roles", roleRepository.findAll());
		
		return modelAndView;
	}
	
	@PostMapping("/adicionar/{userId}")
	public ModelAndView adicionar (@PathVariable(name="userId") Long userId, Role role) {
		ModelAndView modelAndView = new ModelAndView("pages/funcoesuser");
		Usuario usuario = usuarioRepository.findById(userId).get();
		modelAndView.addObject("usuarioObj", usuario);
		modelAndView.addObject("rolesUsuario", usuario.getRoles());
		modelAndView.addObject("roles", roleRepository.findAll());
		
		return modelAndView;
	}
	
	
}
