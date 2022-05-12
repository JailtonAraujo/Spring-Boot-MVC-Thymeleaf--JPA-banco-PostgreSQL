package com.br.projetosbmvc.controllers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.model.Telefone;
import com.br.projetosbmvc.repository.TelefoneRepository;

@Controller
@RequestMapping("**/telefone")
public class TelefoneController implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	TelefoneRepository telefoneRepository;
	
	@PostMapping(value = "/salvar/{idpessoa}")
	public ModelAndView salvar(Telefone telefone, @PathVariable("idpessoa") Long idpessoa) {
		
		telefone.getPessoa().setId(idpessoa);
		Telefone fone = telefoneRepository.saveAndFlush(telefone);
		
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		modelAndView.addObject("telefone", telefone);
		modelAndView.addObject("pessoaobj", new Pessoa());

		return modelAndView;
	}
	
	@GetMapping("/listarfones/{idpessoa}")
	public ModelAndView listar (@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		modelAndView.addObject("telefones", telefoneRepository.findAll());
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
	}
}
