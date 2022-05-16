package com.br.projetosbmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.projetosbmvc.model.Usuario;
import com.br.projetosbmvc.repository.UsuarioRepository;

@Service
@Transactional
public class ImplUserDatailsServer implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findUsuarioByLogin(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario não foi encontrado!");
		}

		return new User(usuario.getLogin(), usuario.getPassword(), usuario.isEnabled(), true, true, true, usuario.getAuthorities()) ;
	}

}
