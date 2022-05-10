package com.br.projetosbmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
/*@EntityScan(basePackages = "com.br.projetosbmvc.model") <= opcional nas versões recentes*/
/*@ComponentScan(basePackages = {"com.br.projetosbmvc.controllers"})*/
/*@EnableJpaRepositories(basePackages = {"com.br.projetosbmvc.repository"})*/
/*@EnableTransactionManagement*/
public class ProjetoSbMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSbMvcApplication.class, args);
	}

}
