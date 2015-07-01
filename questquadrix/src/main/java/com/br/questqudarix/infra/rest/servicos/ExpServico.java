package com.br.questqudarix.infra.rest.servicos;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.br.questqudarix.infra.rest.dao.DirectoryDAO;
import com.br.questqudarix.infra.rest.dto.Contact;

@Path("teste")
public class ExpServico {
	
	@Inject
	DirectoryDAO directoryDAO = new DirectoryDAO();
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contact> getAnnuaire() {
		return directoryDAO.getDirectory();
	}
	
	@Path("/id/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public Contact getContactByID (@PathParam("id") int index) {
		return directoryDAO.getContact(index);
	}
	
	@Path("/id")
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public int addContact (Contact contact) {
		return directoryDAO.addContact(contact);
	}

	@Path("/id/{id}")
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public int deleteContact (@PathParam("id") int index) {
		return directoryDAO.deleteContact(index);
	}

	@Path("/id/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public int updateContact (@PathParam("id") int index, Contact contact) {
		return directoryDAO.updateContact(index, contact);
	}

}
