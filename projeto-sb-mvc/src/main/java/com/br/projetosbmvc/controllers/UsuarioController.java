package com.br.projetosbmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@RequestMapping("/")
	public ModelAndView init () {
		
		ModelAndView modelAndView = new ModelAndView("pages/usuario");
		modelAndView.addObject("usuarios", usuarioRepository.findUsuarioInit());
		
		return modelAndView;
	}
}
