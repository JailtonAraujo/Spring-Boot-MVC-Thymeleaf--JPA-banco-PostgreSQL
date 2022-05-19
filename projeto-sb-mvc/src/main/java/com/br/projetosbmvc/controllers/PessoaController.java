package com.br.projetosbmvc.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.repository.PessoaRepository;
import com.br.projetosbmvc.services.ReportUtil;

@Controller
public class PessoaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ReportUtil reportUtil;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView init() {
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("pessoas", pessoaRepository.findByName("",""));
		
		return modelAndView;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {

			ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
			modelAndView.addObject("pessoaobj", new Pessoa());
			modelAndView.addObject("pessoas", pessoaRepository.findByName("",""));
			
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
		pessoa.getEndereco().setPessoa(pessoa);
		
		pessoaRepository.save(pessoa);
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("pessoas", pessoaRepository.findByName("",""));
		return modelAndView;
	}
	
	/*
	@GetMapping(value = "/listarpessoas")
	public ModelAndView pessoas() {
		
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		
		List<Pessoa> listPessoas = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", listPessoas);
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
	}
	*/
	
	@GetMapping(value = "/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") String idpessoa) {
		
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		Optional<Pessoa> pessoa = pessoaRepository.findById(Long.parseLong(idpessoa));
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		
		return modelAndView;
	}
	
	@GetMapping(value = "/excluirpessoa/{idpessoa}")
	public ModelAndView excluir (@PathVariable("idpessoa") String idpessoa) {
		pessoaRepository.deleteById(Long.parseLong(idpessoa));
		
		ModelAndView andView = new ModelAndView("pages/pagepessoa");
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("pessoas", pessoaRepository.findByName("",""));
		return andView;
	}
	
	@PostMapping(value = "**/findbyname")
	public ModelAndView findByName(@RequestParam(name = "nome") String nome, 
			@RequestParam(name = "sexoPesquisa") String sexoPesquisa) {
		
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		
		modelAndView.addObject("pessoas", pessoaRepository.findByName(nome, sexoPesquisa));
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
	}
	
	@GetMapping(value = "**/findbyname")
	public void printPDF(@RequestParam(name = "nome") String nome, 
			@RequestParam(name = "sexoPesquisa") String sexoPesquisa, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		pessoas = pessoaRepository.findByNameReport(nome, sexoPesquisa);
		
		byte [] pdf = reportUtil.generatedReport(pessoas, sexoPesquisa, request.getServletContext());
		
		response.setContentLength(pdf.length);
		
		response.setContentType("application/octet-stream");
		
		String headerKey ="Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "realtorio.pdf");
		
		response.setHeader(headerKey, headerValue);
		response.getOutputStream().write(pdf);
	}
		
	}

