package com.br.projetosbmvc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Override /*CONFIGURA AS SOLICITAÇÕES DE ACESSO POR HTTP*/
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable() //DESATIVA AS CONFIGURAÇÕES DE MEMORIA PADRÃO DO SECURITY//
		.authorizeRequests() //PERMITIR/RESTRINGIR ACESSOS
		.antMatchers(HttpMethod.GET, "/").permitAll() //QUALQUER USUARIO ACESSA A PAGINA INICIAL
		.anyRequest().authenticated()
		.and().formLogin().permitAll()//PERMITE QUALQUER USUARIO
		.and().logout().permitAll()// MAPEIA A URL DE LOGOUT E INVALIDA USUARIO AUTENTICADO
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}
	
	@Override /*CRIA AUTENTICAÇÃO COM USUARIO NO BANCO DE DADOS OU EM MEMORIA*/
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("jailton")
		.password("123")
		.roles("ADMIN");
		
	}
	
	@Override /*IGNORA URLS ESPECIFICAS*/
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
}
