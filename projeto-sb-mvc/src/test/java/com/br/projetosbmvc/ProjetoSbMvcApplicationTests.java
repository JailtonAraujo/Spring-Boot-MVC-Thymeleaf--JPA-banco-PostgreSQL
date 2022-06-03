package com.br.projetosbmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.repository.OperattionsRepository;

@SpringBootTest
class ProjetoSbMvcApplicationTests {

	@Autowired
	OperattionsRepository operattionsRepository;
	
	@Test
	void TestarPagincao() {
		
		operattionsRepository.findPagination(null);
		
		System.out.println("");
		
		
	}
	
	@Test
	void test() {
		System.out.println("dfddd");
	}

}
