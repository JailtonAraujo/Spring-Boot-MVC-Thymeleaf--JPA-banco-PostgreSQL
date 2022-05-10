package com.br.projetosbmvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.repository.PessoaRepository;

@Controller
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public String init() {
		return "pages/pagepessoa";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
	public String salvar(Pessoa pessoa) {
		
		pessoaRepository.save(pessoa);
		
		return "pages/pagepessoa";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listarpessoas")
	public ModelAndView pessoas() {
		
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		
		List<Pessoa> listPessoas = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", listPessoas);
		
		return modelAndView;
		
		
		
	}
}
