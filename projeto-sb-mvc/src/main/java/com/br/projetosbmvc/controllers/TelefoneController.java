package com.br.projetosbmvc.controllers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.model.Telefone;
import com.br.projetosbmvc.repository.PessoaRepository;
import com.br.projetosbmvc.repository.TelefoneRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("**/telefone")
public class TelefoneController implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	TelefoneRepository telefoneRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@GetMapping("/init/{pessoaid}")
	public ModelAndView init(@PathVariable(name = "pessoaid") Long pessoaid) {
		
		ModelAndView modelAndView = new ModelAndView("pages/telefone");
		
		Pessoa pessoa = new Pessoa();
		modelAndView.addObject("pessoaobj",pessoaRepository.findById(pessoaid).get());
		modelAndView.addObject("telefones", telefoneRepository.findAllFonesByPersonName(pessoaid) );
		
		return modelAndView;
	}
	
	@PostMapping(value = "/salvar/{idpessoa}")
	public ModelAndView salvar(Telefone telefone ,@PathVariable("idpessoa") Long idpessoa) {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(idpessoa);
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("pages/telefone");
		
		
		
		List <Telefone> fones = telefoneRepository.findAllFonesByPersonName(idpessoa);
		
		modelAndView.addObject("pessoaobj", pessoaRepository.findById(idpessoa).get());
		modelAndView.addObject("telefones", fones);

		return modelAndView;
	}
	
	@GetMapping("/excluir/{foneid}")
	public ModelAndView excluir (@PathVariable("foneid") Long foneid) {
		
		Pessoa pessoa = telefoneRepository.findById(foneid).get().getPessoa();
		
		telefoneRepository.deleteById(foneid);
		ModelAndView modelAndView = new ModelAndView("pages/telefone");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.findAllFonesByPersonName(pessoa.getId()));
		modelAndView.addObject("msg", "Excluido com sucesso!");
		
		return modelAndView;
	}
	
}
