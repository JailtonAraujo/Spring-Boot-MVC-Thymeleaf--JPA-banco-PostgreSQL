package com.br.projetosbmvc.controllers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Profissao;
import com.br.projetosbmvc.repository.ProfissaoRepository;

@Controller
@RequestMapping("/profissoes")
public class ProfissaoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProfissaoRepository profissaoRepository;
	
	@RequestMapping("/")
	public ModelAndView init() {
		
		ModelAndView modelAndView = new ModelAndView("pages/profissoes");
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		modelAndView.addObject("profissaoObj", new Profissao());
		
		return modelAndView;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar (Profissao profissao) {
		profissaoRepository.save(profissao);
		ModelAndView modelAndView = new ModelAndView("pages/profissoes");
		modelAndView.addObject("profissaoObj", new Profissao());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		modelAndView.addObject("msg", "Salvo com Sucesso!");
		return modelAndView;
		
	}
	
	@GetMapping("/edit/{profissaoId}")
	public ModelAndView edit (@PathVariable(name = "profissaoId") Long profissaoId) {
		ModelAndView modelAndView = new ModelAndView("pages/profissoes");
		modelAndView.addObject("profissaoObj", profissaoRepository.findById(profissaoId).get());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		return modelAndView;
	}
	
	

}
