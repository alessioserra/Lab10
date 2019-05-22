package it.polito.tdp.porto.model;

import java.io.DataOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Map<Integer,Author> idMap;
	private SimpleGraph<Author, DefaultEdge> grafo;
	private List<Author> listaAutori;
	private PortoDAO dao;
	
	
	public Model() {
		idMap = new HashMap<>();
		listaAutori = new ArrayList<>();
		dao = new PortoDAO();
	}

	public List<Author> getAllAutori(){
		
		listaAutori = dao.getAllAutori(this.idMap);
		
		return listaAutori;
	}
	
	public void creaGrafo() {
		
		//Creo grafo
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		//Aggiunti vertici al grafo
		Graphs.addAllVertices(grafo, this.listaAutori);
		
		//Aggiungo vertici
		List<Accoppiamenti> accoppiamenti = dao.getArchiGrafo();
		
		for (Accoppiamenti a : accoppiamenti) {
			grafo.addEdge( idMap.get(a.getId1()), idMap.get(a.getId2()) );
		}
			
		System.out.println("GRAFO CREATO\n#VERTICI= "+grafo.vertexSet().size()+"\n#ARCHI= "+grafo.edgeSet().size());
	}

	public List<Author> getVicini(Author autore){	
		return Graphs.neighborListOf(this.grafo, autore);
	}
	
}
