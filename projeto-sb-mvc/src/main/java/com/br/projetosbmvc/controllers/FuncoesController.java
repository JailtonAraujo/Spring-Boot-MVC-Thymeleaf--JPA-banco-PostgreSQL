package com.br.projetosbmvc.controllers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Usuario;
import com.br.projetosbmvc.model.UsuariosRole;
import com.br.projetosbmvc.repository.RoleRepository;
import com.br.projetosbmvc.repository.UsuarioRepository;
import com.br.projetosbmvc.repository.UsuariosRoleRepository;

@Controller
@RequestMapping("/funcoes")
public class FuncoesController implements Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UsuariosRoleRepository usuariosRoleRepository;
	
	@RequestMapping("/{idUser}")
	public ModelAndView init (@PathVariable(name = "idUser") Long idUser) {
		ModelAndView modelAndView = new ModelAndView("pages/funcoesuser");
		Usuario usuario = usuarioRepository.findById(idUser).get();
		modelAndView.addObject("usuarioObj", usuario);
		modelAndView.addObject("rolesUsuario", usuario.getRoles());
		modelAndView.addObject("roles", roleRepository.findAll());
		
		return modelAndView;
	}
	
	@PostMapping("/adicionar")
	public ModelAndView adicionar (UsuariosRole usuariosRole) {
		ModelAndView modelAndView = new ModelAndView("pages/funcoesuser");
		if(!usuariosRoleRepository.alreadyExists(usuariosRole.getUsuario().getId(), usuariosRole.getRole().getId())){
	
			usuariosRoleRepository.save(usuariosRole);
			Usuario usuario = usuarioRepository.findById(usuariosRole.getUsuario().getId()).get();
			modelAndView.addObject("usuarioObj", usuario);
			modelAndView.addObject("rolesUsuario", usuario.getRoles());
			modelAndView.addObject("roles", roleRepository.findAll());
			modelAndView.addObject("msg", "Função adicionada com sucesso");
			
		}else {
			Usuario usuario = usuarioRepository.findById(usuariosRole.getUsuario().getId()).get();
			modelAndView.addObject("usuarioObj", usuario);
			modelAndView.addObject("rolesUsuario", usuario.getRoles());
			modelAndView.addObject("roles", roleRepository.findAll());
			modelAndView.addObject("msg", "Esta função já esta associada a este usuario!");
		}
		
		
		return modelAndView;
	}
	
	@GetMapping("/remover")
	public ModelAndView remover(@RequestParam(name = "idUser") Long idUser, @RequestParam(name = "idRole") Long idRole) {
		Long usuarioRoleId = usuariosRoleRepository.findIdUsuarioRoleByUserIdAndRoleId(idUser, idRole);
		
		try {
		usuariosRoleRepository.deleteById(usuarioRoleId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView("pages/funcoesuser");
		
		Usuario usuario = usuarioRepository.findById(idUser).get();
		modelAndView.addObject("usuarioObj", usuario);
		modelAndView.addObject("rolesUsuario", usuario.getRoles());
		modelAndView.addObject("roles", roleRepository.findAll());
		
		return modelAndView;
	}
	
	
}
