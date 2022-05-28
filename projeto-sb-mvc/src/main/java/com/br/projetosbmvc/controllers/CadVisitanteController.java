package com.br.projetosbmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetosbmvc.model.Usuario;
import com.br.projetosbmvc.model.UsuariosRole;
import com.br.projetosbmvc.repository.UsuarioRepository;
import com.br.projetosbmvc.repository.UsuariosRoleRepository;

@Controller
@RequestMapping("**/visitante")
public class CadVisitanteController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuariosRoleRepository roleRepository;

	@GetMapping("/cadastrovisitante")
	public String init() {
		return "cadastrovisitante";
	}

	@PostMapping("**/cadastrar")
	public ModelAndView cadastrar(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("cadastrovisitante");

		if (!usuarioRepository.alreadExistsByLogin(usuario.getLogin())) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));

			UsuariosRole usuarioRole = new UsuariosRole();
			usuarioRole.getRole().setId(3L);
			usuarioRole.setUsuario(usuarioRepository.save(usuario));
			roleRepository.save(usuarioRole);
			modelAndView.addObject("msg", "Visitante cadastrado com sucesso!");
		} else {
			modelAndView.addObject("msg", "Login ja Existente, tente outro por favor!");
		}

		return modelAndView;
	}
}
