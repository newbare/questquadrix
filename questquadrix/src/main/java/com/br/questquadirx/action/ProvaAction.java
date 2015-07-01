package com.br.questquadirx.action;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.br.questquadirx.dominio.Prova;
import com.br.questquadirx.servico.ProvaService;

/**
 * @author Jefera
 */
@ManagedBean
@ViewScoped
public class ProvaAction extends SpringBeanAutowiringSupport {

	protected static final Log log = LogFactory.getLog(ProvaAction.class);

	@Inject
	private ProvaService controller;

	private String state;
	private List<Prova> items;
	private Prova item;

	/**
	 * 
	 */
	public ProvaAction() {
		log.info("Bean constructor called.");
	}

	/**
	 * 
	 */
	@PostConstruct
	private void postConstruct() {
		log.info("Bean @PostConstruct called.");
		state = "READ";
		items = controller.list();
	}

	/**
	 * Clears entity items
	 */
	public void clearItems() {
		if (items != null) {
			items.clear();
		}
	}

	/**
	 * Clears entity item
	 */
	public void clearItem() {
		try {
			// Instantiating via reflection was used here for generic purposes
			item = Prova.class.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("generic.bean.unableToCleanViewData");
		}
	}

	/**
	 * @param event
	 */
	public void create() {
		controller.create(item);
		items = controller.list();
		item = null;
	}

	/**
	 * @param event
	 */
	public void update() {
		controller.update(item);
		items = controller.list();
		item = null;
	}

	public void delete() {
		controller.delete(item.getIdProva());
		items = controller.list();
		item = null;
	}

	/**
	 * 
	 */
	@PreDestroy
	private void preDestroy() {
		log.info("Bean @PreDestroy called.");
	}

	/*
	 * Getters and Setters
	 */

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Prova> getItems() {
		return items;
	}

	public void setItems(List<Prova> items) {
		this.items = items;
	}

	public Prova getItem() {
		return item;
	}

	public void setItem(Prova item) {
		this.item = item;
	}

}