package com.br.projetosbmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplUserDatailsServer implUserDatailsServer;

	@Override/*CONFIGURA AS SOLICITAÇÕES DE ACESSO POR HTTP*/
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()/*DESABILITA AS CONFIGURAÇÕES DE MEMORIA PADRÃO*/
		.authorizeRequests() /*PERMITIR/RESRTINGIR ACESSOS*/
		.antMatchers(HttpMethod.GET,"/").permitAll() /*PERMITE QUALQUER USUARIO ACESSAR*/
		.antMatchers("/visitante/cadastrovisitante").permitAll()
		.antMatchers("/visitante/cadastrar").permitAll()
		.antMatchers(HttpMethod.GET).hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").permitAll() /*PERMITE QUALQUER USUAIRIO*/
		.defaultSuccessUrl("/cadastropessoa")
		.failureUrl("/login?error=Usuario ou senha incorretos!")
		.and().logout().logoutSuccessUrl("/login") /*MAPEIA A URL DE LOGOUT E INVALIDA O USUARIO LOGADO*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override /*CRIA AUTENTICAÇÃO COM USUARIO  EM BANCO OU EM MEMORIA*/
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implUserDatailsServer)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		/*
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("jailton")
		.password("1234")
		.roles("ADMIN");
		*/
	}
	
	@Override /*IGNORA URLS ESPECIFICAS*/
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
		
	}
}
