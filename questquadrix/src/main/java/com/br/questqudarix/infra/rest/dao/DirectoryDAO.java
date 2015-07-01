package com.br.questqudarix.infra.rest.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import com.br.questqudarix.infra.rest.dto.Contact;

@Singleton
@Named
public class DirectoryDAO {
	
	List<Contact> directory = new ArrayList<Contact>();
	
	public DirectoryDAO() {
		directory.add(new Contact("Duplantier", "Bebert", 38));
		directory.add(new Contact("Lasalle", "Gégé", 53));
		directory.add(new Contact("Martin", "Loulou", 25));
		directory.add(new Contact("Dupont", "Riton", 34));
		directory.add(new Contact("Ménard", "Bibi", 47));
	}
	
	public List<Contact> getDirectory () {
		System.out.println("LIST : " + directory.toString());
		return directory;
	}
	
	public Contact getContact (int index) {
		System.out.println("READ : " + directory.get(index).toString());
		return directory.get(index);
	}
	
	public int addContact (Contact contact) {
		try {
			directory.add(contact);
		} catch (Exception e) {
			return 0;
		}
		System.out.println("CREATE : " + contact.toString());
		return 1;
	}

	public int deleteContact (int index) {
		Contact contact;
		try {
			contact = directory.get(index);
			directory.remove(index);
		} catch (Exception e) {
			return 0;
		}
		System.out.println("DELETE : " + contact.toString());
		return 1;
	}
	
	public int updateContact (int index, Contact contact) {
		if (index < directory.size()) {
			directory.set(index, contact);
			System.out.println("UPDATE : " + contact.toString());
			return 1;
		} else {
			return 0;
		}
	}
}
