package it.polito.tdp.porto.model;

import java.io.DataOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.sun.javafx.geom.Edge;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Map<Integer,Author> idMap;
	private SimpleGraph<Author, DefaultEdge> grafo;
	private List<Author> listaAutori;
	private PortoDAO dao;
	private Map<Integer,Paper> idArticoli;
	private List<Accoppiamenti> accoppiamenti;
	
	
	public Model() {
		idMap = new HashMap<>();
		listaAutori = new ArrayList<>();
		dao = new PortoDAO();
		idArticoli = new HashMap<>();
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
		accoppiamenti = dao.getArchiGrafo();
		
		for (Accoppiamenti a : accoppiamenti) {
			grafo.addEdge( idMap.get(a.getId1()), idMap.get(a.getId2()) );
		}
			
		System.out.println("GRAFO CREATO\n#VERTICI= "+grafo.vertexSet().size()+"\n#ARCHI= "+grafo.edgeSet().size());
	}

	public List<Author> getVicini(Author autore){	
		return Graphs.neighborListOf(this.grafo, autore);
	}
	
	public List<Paper> getCamminoMinimo(Author partenza, Author arrivo){
			
		//Trovo cammino minimo di autori
			DijkstraShortestPath<Author, DefaultEdge> dijkstra = new DijkstraShortestPath<>(this.grafo);
			GraphPath<Author, DefaultEdge> path = dijkstra.getPath(partenza, arrivo);
			
			System.out.println(path.getVertexList().size());
			
			//Carico lista di tutti gli articoli e lista autori del cammino
			dao.getAllArticoli(this.idArticoli);
			
			//Ottengo la lista degli archi
			List<DefaultEdge> listEdge = path.getEdgeList();
			
			//Inizializzo risultato
			List<Paper> risultato = new ArrayList<>();
			
			//Scorro tutti gli archi per trovare il primo articolo che li collega
			for (DefaultEdge e : listEdge) risultato.add(trovaPaper(e));
			
			//Restituisco risultato
			return risultato;		
	}
	
	//Trovo il primo articolo che trovo, dato l'arco
	public Paper trovaPaper(DefaultEdge edge) {
		
		//Trovo gli estremi dell'arco
	    Author a1 = grafo.getEdgeSource(edge);
	    Author a2 = grafo.getEdgeTarget(edge);
		
		//Scorro gli accoppiamenti, appena trovo il primo lo ritorno
		for (Accoppiamenti a: this.accoppiamenti) {	
			if (a1.getId()==a.getId1() && a2.getId()==a.getId2()) return idArticoli.get(a.getIdArticolo());
		}
		
		return null;
		
	}
}
