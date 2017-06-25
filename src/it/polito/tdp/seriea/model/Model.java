package it.polito.tdp.seriea.model;

import java.util.*;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	private List<Season> seasons;
	private SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge>graph;
	private TeamIdMap mapTeams;
	private List<Team> best;
	private List<DefaultWeightedEdge> bestArchi;
	
	public List<Season> getSeasons(){
		if(seasons==null){
			SerieADAO dao = new SerieADAO();
			seasons= dao.listSeasons();
		}
		return seasons;
	}
	
	public List<Team> getTeamsForSeason(Season s){
		SerieADAO dao = new SerieADAO();
		
		if(mapTeams==null){
			mapTeams=new TeamIdMap();
		}
		
		return dao.getTeamsForSeason(s, mapTeams);
	}

	public void createGraph(Season s) {
		graph= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(graph, this.getTeamsForSeason(s));
		//System.out.println(graph.vertexSet());
		
		SerieADAO dao = new SerieADAO();
		
		//System.out.println(dao.getMatchesForSeason(s, mapTeams).toString()+"\n");
		
		for(Match m: dao.getMatchesForSeason(s, mapTeams)){
			DefaultWeightedEdge e1= graph.addEdge(m.getHomeTeam(),m.getAwayTeam());
			
			if(e1!=null){
				if(m.getFtr().compareTo("H")==0){
					graph.setEdgeWeight(e1, 1);
				}	else if(m.getFtr().compareTo("D")==0){
					graph.setEdgeWeight(e1, 0);
				} else {
					graph.setEdgeWeight(e1, -1);
				}
			}
		}
		System.out.println(graph.vertexSet().size()+"\n");
		System.out.println(graph.edgeSet().size()+"\n");
		System.out.println(graph.toString());
	}
	
	public List<TeamAndScore> getClassificaCampionato(Season s){
		List<TeamAndScore> lista = new ArrayList<>();	
		
		for(Team t: graph.vertexSet()){
			//metto il punteggio a 0 ogni volta!
			int punteggio=0;
			
			for(DefaultWeightedEdge edge: graph.outgoingEdgesOf(t)){
				if(graph.getEdgeWeight(edge)==1)
					punteggio +=3;
				if(graph.getEdgeWeight(edge)==0){
					punteggio +=1;
				}
			}
			for(DefaultWeightedEdge ed: graph.incomingEdgesOf(t)){
				if(graph.getEdgeWeight(ed)==-1)
					punteggio +=3;
				if(graph.getEdgeWeight(ed)==0){
					punteggio +=1;
				}
			}
			
			lista.add(new TeamAndScore(t, punteggio));
		}
		
		Collections.sort(lista);
		return lista;
	}
	
	public List<Team> getLongestPath(){
		List<DefaultWeightedEdge> parzialeArchi= new ArrayList<>();
		bestArchi = new ArrayList<>();
		best = new ArrayList<>();
		
		recursive(parzialeArchi);
		
		for(DefaultWeightedEdge edge: bestArchi){
			best.add(graph.getEdgeSource(edge));
		}
		
		best.add(graph.getEdgeTarget(bestArchi.get(bestArchi.size()-1)));
		
		System.out.println("best: "+best.toString());
		return best;
	}

	private void recursive(List<DefaultWeightedEdge> parzialeArchi) {
		// condizione terminazione == tutte le possibili ==> termina da solo
		//controllo se la dim di parziale è > best aggiorno best
		//System.out.println(parzialeArchi.toString()+"\n");
		
		if(parzialeArchi.size() >= bestArchi.size()){
			bestArchi.clear();
			bestArchi.addAll(parzialeArchi);
			System.out.println("bestArchi: "+bestArchi.toString()+"\n");
		}
		
		for(DefaultWeightedEdge edge : graph.edgeSet()){
			if(graph.getEdgeWeight(edge)==1){
				if(!parzialeArchi.contains(edge) && (parzialeArchi.size()==0 || graph.getEdgeTarget(parzialeArchi.get(parzialeArchi.size()-1)).equals(graph.getEdgeSource(edge)))){
					parzialeArchi.add(edge);
					recursive(parzialeArchi);
					parzialeArchi.remove(edge);
				}
			}
		}
				
	}
	public void RiduciGrafo(){
		//visto che la ricorsione è troppo lunga, per la stagione 2005/2006 riduco il graph eliminando alcuni vertici
		//riduzione coincidente con quella fatta dal prof nella sua versione
				graph.removeVertex(new Team("Fiorentina"));
				graph.removeVertex(new Team("Livorno"));
				graph.removeVertex(new Team("Ascoli"));
				graph.removeVertex(new Team("Inter"));
				graph.removeVertex(new Team("Juventus"));
				graph.removeVertex(new Team("Lazio"));
				graph.removeVertex(new Team("Parma"));
				graph.removeVertex(new Team("Reggina"));
				graph.removeVertex(new Team("Siena"));
				graph.removeVertex(new Team("Udinese"));
				graph.removeVertex(new Team("Milan"));
				graph.removeVertex(new Team("Palermo"));
				//graph.removeVertex(new Team("Cagliari"));
				graph.removeVertex(new Team("Chievo"));
				//graph.removeVertex(new Team("Empoli"));
				graph.removeVertex(new Team("Lecce"));
				graph.removeVertex(new Team("Messina"));
				//graph.removeVertex(new Team("Roma"));
				graph.removeVertex(new Team("Sampdoria"));
				graph.removeVertex(new Team("Treviso"));
				System.err.println("Vertici rimasti: "+graph.vertexSet().size());
				System.err.println(graph.vertexSet().toString());
				System.err.println("Archi rimasti: "+graph.edgeSet().size());
	}

}
