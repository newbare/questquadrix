package com.br.questqudarix.infra.rest.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.br.questquadirx.dominio.Prova;
import com.br.questquadirx.servico.ProvaService;

@Named
@Path("prova")
public class ProvaRest extends SpringBeanAutowiringSupport implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 339661291934991952L;
	
	
	@Inject
	ProvaService provaService;	
	/*public void setProvaService(ProvaService provaService) {
		this.provaService = provaService;
	}
*/
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prova> getPorvas() {
		return provaService.list();
	}
	
	@Path("/id/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public Prova getProvaByID (@PathParam("id") int index) {
		return provaService.read(index);
	}
	
	@Path("/id")
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public int addProva (Prova prova) {
		try {			
			provaService.create(prova);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	

	@Path("/id/{id}")
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public int deleteProva (@PathParam("id") int index) {
		try {
			provaService.delete(index);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	@Path("/id/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public int updateProva (@PathParam("id") int index, Prova prova) {
		try {
			provaService.update(index, prova);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

}
