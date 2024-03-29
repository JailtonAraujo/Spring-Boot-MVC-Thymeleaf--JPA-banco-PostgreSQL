package com.br.projetosbmvc.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.br.projetosbmvc.dto.PessoaDTO;
import com.br.projetosbmvc.model.FotoPessoa;
import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.repository.PessoaRepository;
import com.br.projetosbmvc.repository.ProfissaoRepository;
import com.br.projetosbmvc.services.PessoaServices;
import com.br.projetosbmvc.services.ReportUtil;

@Controller
public class PessoaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ReportUtil reportUtil;
	
	@Autowired
	private PessoaServices services;

	@Autowired
	private ProfissaoRepository profissaoRepository;

	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView init() {
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("pessoas", services.findPaginator());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());

		return modelAndView;
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa", consumes = { "multipart/form-data" })
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult, final MultipartFile fotoPerfil)
			throws IOException {

		if (bindingResult.hasErrors()) {

			ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
			modelAndView.addObject("pessoaobj", new Pessoa());
			modelAndView.addObject("pessoas", services.findPaginator());
			modelAndView.addObject("profissoes", profissaoRepository.findAll());

			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}

			modelAndView.addObject("msg", msg);
			return modelAndView;
		}

		services.salvar(pessoa, fotoPerfil);
		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("pessoas", services.findPaginator());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		return modelAndView;
	}

	@GetMapping(value = "**/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") String idpessoa) {

		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		Optional<Pessoa> pessoa = pessoaRepository.findById(Long.parseLong(idpessoa));

		FotoPessoa temp = new FotoPessoa();
		temp.setId(pessoa.get().getFotoPessoa().getId());
		if (pessoa.get().getFotoPessoa().getFotoUser() != null) {
			String fotoBase64 = Base64.getEncoder().encodeToString(pessoa.get().getFotoPessoa().getFotoUser());
			fotoBase64 = "data:" + pessoa.get().getFotoPessoa().getTypeImage() + ";base64," + fotoBase64;
			temp.setFotoBase64(fotoBase64);
		}else {
			temp.setFotoBase64(null);
		}
		pessoa.get().setFotoPessoa(temp);

		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("pessoas", services.findPaginator());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());

		return modelAndView;
	}

	@GetMapping(value = "/excluirpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") String idpessoa) {
		pessoaRepository.deleteById(Long.parseLong(idpessoa));

		ModelAndView andView = new ModelAndView("pages/pagepessoa");
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("pessoas", services.findPaginator());
		andView.addObject("profissoes", profissaoRepository.findAll());

		return andView;
	}

	@PostMapping(value = "**/findbyname")
	public ModelAndView findByName(@RequestParam(name = "nomepesquisa") String nomepesquisa,
			@RequestParam(name = "sexopesquisa") String sexopesquisa, @PageableDefault(size=5, sort = {"nome"}) Pageable pageable ) {

		ModelAndView modelAndView = new ModelAndView("pages/pagepessoa");
		
		modelAndView.addObject("pessoas", services.findPessoaByNameAndSexoPagitanor(nomepesquisa, sexopesquisa, pageable));
		
		modelAndView.addObject("sexopesquisa", sexopesquisa);
		modelAndView.addObject("nomepesquisa", nomepesquisa);
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());

		return modelAndView;
	}

	@GetMapping(value = "**/findbyname")
	public void printPDF(@RequestParam(name = "nomepesquisa") String nomepesquisa,
			@RequestParam(name = "sexopesquisa") String sexopesquisa, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<Pessoa> pessoas = new ArrayList<Pessoa>();

		pessoas = pessoaRepository.findByNameReport(nomepesquisa, sexopesquisa);

		byte[] pdf = reportUtil.generatedReport(pessoas, "pessoaReport", request.getServletContext());

		response.setContentLength(pdf.length);

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "realtorio.pdf");

		response.setHeader(headerKey, headerValue);
		response.getOutputStream().write(pdf);
	}
	
	@GetMapping("/paginator")
	public ModelAndView pagination(@PageableDefault(size = 5) Pageable pageable, ModelAndView view,
			@RequestParam(name = "nomepesquisa") String nomepesquisa, @RequestParam("sexopesquisa") String sexopesquisa) {
		
	
			view.addObject("pessoas", services.findPessoaByNameAndSexoPagitanor(nomepesquisa, sexopesquisa, pageable));
			
		view.addObject("pessoaobj", new Pessoa());
		view.addObject("profissoes", profissaoRepository.findAll());
		view.addObject("nomepesquisa", nomepesquisa);
		view.addObject("secopesquisa", sexopesquisa);
		
		view.setViewName("pages/pagepessoa");
		return view;
		
	}

}
