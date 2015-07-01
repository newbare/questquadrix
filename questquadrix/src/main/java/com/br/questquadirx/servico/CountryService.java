package com.br.questquadirx.servico;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.br.questquadirx.dominio.Country;
import com.br.questquadirx.repositorio.CountryRepository;

/**
 * @author Jefera
 */
@Named
public class CountryService {

	
	private CountryRepository countryRepository;

	
	@Inject
	public void setCountryRepository(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
		countryRepository.setPersistentClass(Country.class);
	}

	
	public List<Country> list() {
		return countryRepository.listar();
	}

	public Country read(Long id) {
		return countryRepository.buscarPor(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void create(Country country) {
		countryRepository.inserir(country);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Country update(Country country) {
		return countryRepository.alterar(country);
	}

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * delete(Country country) { repository.excluir(country); }
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		countryRepository.remover(id);
	}

}