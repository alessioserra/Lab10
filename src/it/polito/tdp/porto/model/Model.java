package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Map<Integer,Author> idMap;
	
	
	public Model() {
		idMap = new HashMap<>();
	}

	public List<Author> getAllAutori(Map<Integer,Author> idMap){
		PortoDAO dao = new PortoDAO();
		return dao.getAllAutori(idMap);
	}

}
