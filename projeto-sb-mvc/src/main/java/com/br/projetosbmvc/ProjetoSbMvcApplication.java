package com.br.projetosbmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.br.projetosbmvc.model")/*<= opcional nas versões recentes*/
public class ProjetoSbMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSbMvcApplication.class, args);
	}

}
