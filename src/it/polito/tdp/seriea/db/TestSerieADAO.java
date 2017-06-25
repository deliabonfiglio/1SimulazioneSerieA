package it.polito.tdp.seriea.db;

import java.time.LocalDate;
import java.util.List;
import it.polito.tdp.seriea.model.BigMatch;
import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.MatchIdMap;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamIdMap;

public class TestSerieADAO {

	public static void main(String[] args) {
		SerieADAO dao = new SerieADAO() ;
		
		List<Season> seasons = dao.listSeasons() ;
		System.out.println(seasons);
		
		TeamIdMap map = new TeamIdMap();
		List<Team> teams = dao.listTeams(map) ;
		//System.out.println(teams);

		Season s = seasons.get(1);
		MatchIdMap mapm= new MatchIdMap();
		
		List<BigMatch>  m = dao.getMatchForSeason(s, map);
		System.out.println("Bigm:"+m);
		
		LocalDate inizio = LocalDate.of(2015, 8, 29);
		LocalDate fine = LocalDate.of(2015, 12, 29);
				
		List<Match> m2 = dao.getMatchesForPeriod(inizio, fine, map);
		System.out.println(m2);
		
		List<Match> m3 = dao.getMatchesForTeams(new Team("Inter"), new Team("Lecce"), map);
		System.out.println(m3);
		
		System.out.println(dao.getNumeroDiPartiteVinteTraDueTeam(new Team("Juventus"), new Team("Milan"), map));
		
		List<Match> l= dao.getPartiteVinteInCasaDA(new Team("Juventus"), map, mapm);
		System.out.println(l.toString()+"\n"+l.size());
		
		List<Match> listaaaaa=dao.getAllMatchDiUnTeam(new Team("Inter"), map);
		System.out.println("g"+listaaaaa);
		
		List<Match> lista1=dao.getAllMatchDiUnTeam(new Team("Inter"), map, mapm);
		System.out.println("\n---h"+lista1);
	}

}
