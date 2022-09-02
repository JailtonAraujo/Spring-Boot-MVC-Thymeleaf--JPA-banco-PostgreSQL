package com.br.projetosbmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Usuario;
import com.br.projetosbmvc.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;


	@RequestMapping("/")
	public ModelAndView init() {
		
		ModelAndView modelAndView = new ModelAndView("pages/usuario");
		modelAndView.addObject("usuarios", usuarioRepository.findAll());
		modelAndView.addObject("usuarioObj", new Usuario());

		return modelAndView;
	}

	@PostMapping(value="**/salvar")
	public ModelAndView salvar(Usuario usuario ) {
		ModelAndView modelAndView = new ModelAndView("pages/usuario");
		
		if(usuarioRepository.alreadExistsByLogin(usuario.getLogin()) == 0) {
			usuarioRepository.save(usuario);
			modelAndView.addObject("msg", "Salvo com sucesso!");
		}else {
			modelAndView.addObject("msg", "Nome de usuario ja existente, tente outro!");
		}
		modelAndView.addObject("usuarios", usuarioRepository.findUsuarioInit());
		modelAndView.addObject("usuarioObj", new Usuario());

		return modelAndView;
	}

	@GetMapping("/deletar/{idUser}")
	public ModelAndView deletar(@PathVariable(name = "idUser") Long idUser) {

		usuarioRepository.deleteById(idUser);

		ModelAndView modelAndView = new ModelAndView("pages/usuario");
		modelAndView.addObject("msg", "Deletado com sucesso!");
		modelAndView.addObject("usuarios", usuarioRepository.findUsuarioInit());
		modelAndView.addObject("usuarioObj", new Usuario());

		return modelAndView;
	}

	@GetMapping("/editar/{idUser}")
	public ModelAndView editar(@PathVariable(name = "idUser") Long idUser) {
		ModelAndView modelAndView = new ModelAndView("pages/usuario");
		modelAndView.addObject("usuarioObj", usuarioRepository.findById(idUser).get());
		modelAndView.addObject("msg", "Usuario carregado para Edição");
		modelAndView.addObject("usuarios", usuarioRepository.findUsuarioInit());

		return modelAndView;
	}

}
