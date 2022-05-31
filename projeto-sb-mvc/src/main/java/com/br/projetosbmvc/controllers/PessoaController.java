package com.br.projetosbmvc.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.FotoPessoa;
import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.repository.PessoaRepository;
import com.br.projetosbmvc.repository.ProfissaoRepository;
import com.br.projetosbmvc.services.ReportUtil;

@Controller
public class PessoaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ReportUtil reportUtil;
	
	@Autowired
	private ProfissaoRepository profissaoRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView init() {
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("pessoas", pessoaRepository.findByName("",""));
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		
		return modelAndView;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa", consumes = {"multipart/form-data"})
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult, final MultipartFile fotoPerfil) throws IOException {
		
		if(bindingResult.hasErrors()) {

			ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
			modelAndView.addObject("pessoaobj", new Pessoa());
			modelAndView.addObject("pessoas", pessoaRepository.findByName("",""));
			modelAndView.addObject("profissoes", profissaoRepository.findAll());
			
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
		FotoPessoa fotoPessoa = new FotoPessoa();
		fotoPessoa.setFotoUser(fotoPerfil.getBytes());
		fotoPessoa.setNameImage(fotoPerfil.getOriginalFilename());
		fotoPessoa.setTypeImage(fotoPerfil.getContentType());
		fotoPessoa.setPessoa(pessoa);
		pessoa.getEndereco().setPessoa(pessoa);
		pessoa.setFotoPessoa(fotoPessoa);
		
		pessoaRepository.save(pessoa);
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("pessoas", pessoaRepository.findByName("",""));
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		return modelAndView;
	}
	
	@GetMapping(value = "/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") String idpessoa) {
		
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		Optional<Pessoa> pessoa = pessoaRepository.findById(Long.parseLong(idpessoa));
		
		if(pessoa.get().getFotoPessoa().getFotoUser().length > 0) {
		String fotoBase64 = Base64.getEncoder().encodeToString(pessoa.get().getFotoPessoa().getFotoUser());
		fotoBase64 = "data:image/"+pessoa.get().getFotoPessoa().getTypeImage()+";base64,"+fotoBase64;
		pessoa.get().getFotoPessoa().setFotoBase64(fotoBase64);
		}
		
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		
		
		return modelAndView;
	}
	
	@GetMapping(value = "/excluirpessoa/{idpessoa}")
	public ModelAndView excluir (@PathVariable("idpessoa") String idpessoa) {
		pessoaRepository.deleteById(Long.parseLong(idpessoa));
		
		ModelAndView andView = new ModelAndView("pages/pagepessoa");
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("pessoas", pessoaRepository.findByName("",""));
		andView.addObject("profissoes", profissaoRepository.findAll());
		
		return andView;
	}
	
	@PostMapping(value = "**/findbyname")
	public ModelAndView findByName(@RequestParam(name = "nome") String nome, 
			@RequestParam(name = "sexoPesquisa") String sexoPesquisa) {
		
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		
		modelAndView.addObject("pessoas", pessoaRepository.findByName(nome, sexoPesquisa));
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		
		return modelAndView;
	}
	
	@GetMapping(value = "**/findbyname")
	public void printPDF(@RequestParam(name = "nome") String nome, 
			@RequestParam(name = "sexoPesquisa") String sexoPesquisa, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		pessoas = pessoaRepository.findByNameReport(nome, sexoPesquisa);
		
		byte [] pdf = reportUtil.generatedReport(pessoas, "pessoaReport", request.getServletContext());
		
		response.setContentLength(pdf.length);
		
		response.setContentType("application/octet-stream");
		
		String headerKey ="Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "realtorio.pdf");
		
		response.setHeader(headerKey, headerValue);
		response.getOutputStream().write(pdf);
	}
		
	}

