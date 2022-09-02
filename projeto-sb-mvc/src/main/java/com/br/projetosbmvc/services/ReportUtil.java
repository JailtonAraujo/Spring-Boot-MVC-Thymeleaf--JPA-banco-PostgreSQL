package com.br.projetosbmvc.services;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings("rawtypes") 
@Component
public class ReportUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*Retorna pdf em byte para download no navegador*/
	public byte [] generatedReport(List listData, String nameReport, ServletContext context)throws Exception {
		
		/*Cria lista de dados para o relatorio baseado na lista de objetos passada*/
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listData); 
		
		/*Carrega o caminho do arquivo jasper*/
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("reports/"+nameReport+".jasper");
		
		/*Carrega o arquivo jasper passando od dados*/
		JasperPrint jasperPrint = JasperFillManager.fillReport(stream, new HashedMap(),dataSource);
		
		/*Exporta para byte para fazer download do PDF*/
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
}	
