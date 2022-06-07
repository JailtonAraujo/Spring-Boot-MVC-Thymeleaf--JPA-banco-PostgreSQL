package com.br.projetosbmvc.services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.br.projetosbmvc.dto.PessoaDTO;
import com.br.projetosbmvc.model.FotoPessoa;
import com.br.projetosbmvc.model.Pessoa;
import com.br.projetosbmvc.repository.PessoaRepository;

@Service
public class PessoaServices implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PessoaRepository pessoaRepository; 
	
	public Page<PessoaDTO> findPaginator(){
		
		Page<Pessoa> pessoas = pessoaRepository.findAll(PageRequest.of(0, 5));
		List<PessoaDTO> pessoasDto = pessoas.stream().map(obj -> new PessoaDTO(obj)).collect(Collectors.toList());
		
		Page<PessoaDTO> pageDTO = new PageImpl<PessoaDTO>(pessoasDto, pessoas.getPageable(), pessoas.getTotalElements());
		
		return pageDTO;
	}
	
	public Page<PessoaDTO> findPessoaByNameAndSexoPagitanor(String nomepesquisa, String sexopesquisa, Pageable pageable){
		
		Page<Pessoa> pessoas = pessoaRepository.findPessoaByNameAndSexoPagitanor(nomepesquisa, sexopesquisa, pageable);
		List<PessoaDTO> pessoasDTO = pessoas.stream().map(obj -> new PessoaDTO(obj)).collect(Collectors.toList());
		
		Page <PessoaDTO> pageDTO = new PageImpl<PessoaDTO>(pessoasDTO, pessoas.getPageable(), pessoas.getTotalElements());
		
		return pageDTO;
		
		
	}
	
	public void salvar(Pessoa pessoa, MultipartFile multipartFile) {
		
		FotoPessoa fotoPessoa = new FotoPessoa();
		
		try {
			
			if (multipartFile.getSize() > 0) {
				
				//REDIMENSIONANDO A IMAGE DE PERFIL DE PESSOA//
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				if(multipartFile.getInputStream() != null && multipartFile != null) {
					byte [] imageByte = multipartFile.getBytes();
					BufferedImage bufferedImage = ImageIO.read( new ByteArrayInputStream(imageByte));
					
					int type = bufferedImage.getType() == 0? bufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
					
					int largura = 60;
					int altura = 60;
					
					//CRIANDO MINIATURA//
					BufferedImage resizeImage = new BufferedImage(largura, altura, type);
					Graphics2D graphics2d = resizeImage.createGraphics();
					graphics2d.drawImage(bufferedImage, 0, 0, largura, altura, null);
					graphics2d.dispose();
					
					//REESCREVENDO A IMAGEM EM TAMANHO MENOR//
					String extensao = multipartFile.getContentType().split("\\/")[1];
					ImageIO.write(resizeImage, extensao, arrayOutputStream);
				}
				
				fotoPessoa.setFotoUser(arrayOutputStream.toByteArray());
				fotoPessoa.setNameImage(multipartFile.getOriginalFilename());
				fotoPessoa.setTypeImage(multipartFile.getContentType());
				fotoPessoa.setId(pessoa.getFotoPessoa().getId());

			} else {
				if (pessoa.getId() != null && multipartFile.getSize() <= 0) {// Caso onde Mantém a mesma foto
					fotoPessoa = pessoaRepository.findFoto(pessoa.getId());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		pessoa.setFotoPessoa(fotoPessoa);
		fotoPessoa.setPessoa(pessoa);
		pessoa.getEndereco().setPessoa(pessoa);
		
		pessoaRepository.save(pessoa);
	}

}
