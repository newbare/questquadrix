package com.br.questqudarix.infra.rest.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.br.questquadirx.dominio.Banca;
import com.br.questquadirx.servico.BancaService;

@Named
@Path("banca")
public class BancaRest extends SpringBeanAutowiringSupport implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 339661291934991952L;
	
	
	@Inject
	BancaService bancaService;	

	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Banca> getBancas() {
		return bancaService.list();
	}	

}
