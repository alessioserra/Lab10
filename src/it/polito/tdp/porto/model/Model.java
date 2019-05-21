package it.polito.tdp.porto.model;

import java.util.List;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	public List<Author> getAllAutori(){
		PortoDAO dao = new PortoDAO();
		return dao.getAllAutori();
	}

}
