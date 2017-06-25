package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import it.polito.tdp.seriea.model.BigMatch;
import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.MatchIdMap;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamIdMap;

public class SerieADAO {
	
	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons order by season " ;
		
		List<Season> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Season(res.getInt("season"), res.getString("description"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Team> listTeams(TeamIdMap map) {
		String sql = "SELECT team FROM teams" ;
		
		List<Team> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Team t = map.get(res.getString("team"));
				
				if(t==null){
					t= new Team(res.getString("team"));
					t= map.put(t);
				}
				
				result.add(t) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Team> getTeamsForSeason(Season s, TeamIdMap map) {
		String sql = "select distinct t.team "+
						"from teams as t, matches as m "+
						"where m.Season=?  and m.HomeTeam=t.team ";
		
		List<Team> result= new ArrayList<>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getSeason());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Team t = map.get(res.getString("team"));
				
				if(t==null){
					t= new Team(res.getString("team"));
					t= map.put(t);
					
				}
				result.add(t) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Match> getMatchesForSeason(Season s, TeamIdMap map){
		String sql ="select m.match_id, m.Season, m.`Div`, m.Date, m.HomeTeam as h, m.AwayTeam as a, m.FTHG, m.FTAG, m.FTR "+
					"from matches as m, teams as t1, teams as t2 "+
					"where m.Season=? and m.`Div`='I1' and m.HomeTeam=t1.team and m.AwayTeam=t2.team ";
		
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getSeason());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(map.get(res.getString("h"))==null || map.get(res.getString("a"))==null)
					System.out.println("Errore team assente\n");
				
				else {
					
					result.add( new Match(
						res.getInt("match_id"), s, res.getString("Div"),
						res.getDate("Date").toLocalDate(), map.get(res.getString("h")),
						map.get(res.getString("a")), res.getInt("FTHG"), res.getInt("FTAG"),
						res.getString("FTR"))) ;
				}
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<BigMatch> getMatchForSeason(Season s, TeamIdMap map){
		String sql ="select m.* "+
					"from matches as m "+
					"where m.Season=? ";
		
		List<BigMatch> result = new ArrayList<BigMatch>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getSeason());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(map.get(res.getString("HomeTeam"))==null || map.get(res.getString("AwayTeam"))==null)
					System.out.println("Errore team assente\n");
				
				else {
					
					result.add( new BigMatch(
						res.getInt("match_id"), s, 
						res.getString("Div"),
						res.getDate("Date").toLocalDate(), 
						map.get(res.getString("HomeTeam")),
						map.get(res.getString("AwayTeam")), 
						res.getInt("FTHG"), 
						res.getInt("FTAG"),
						res.getString("FTR"),
						res.getInt("HTHG"), 
						res.getInt("HTAG"), 
						res.getString("HTR"), 
						res.getInt("HS"), 
						res.getInt("AS"), 
						res.getInt("HST"), 
						res.getInt("AST"), 
						res.getInt("HF"), 
						res.getInt("AF"), 
						res.getInt("HC"),
						res.getInt("AC"), 
						res.getInt("HY"), 
						res.getInt("AY"), 
						res.getInt("HR"),
						res.getInt("AR"))) ;
				}
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Match> getMatchesForPeriod(LocalDate inizio, LocalDate fine, TeamIdMap map){
		String sql ="select m.match_id, m.Season, m.`Div`, m.Date, m.HomeTeam as h, m.AwayTeam as a, m.FTHG, m.FTAG, m.FTR "+
					"from matches as m "+
					"where m.Date >= ? and m.Date<=? ";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDate(1, Date.valueOf(inizio));
			st.setDate(2, Date.valueOf(fine));
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(map.get(res.getString("h"))==null || map.get(res.getString("a"))==null)
					System.out.println("Errore team assente\n");
				
				else {
					
					result.add( new Match(
						res.getInt("match_id"), 
						null,
						//s, 
						res.getString("Div"),
						res.getDate("Date").toLocalDate(), map.get(res.getString("h")),
						map.get(res.getString("a")), res.getInt("FTHG"), res.getInt("FTAG"),
						res.getString("FTR"))) ;
				}
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Match> getMatchesForTeams(Team t1, Team t2, TeamIdMap map){
		String sql ="select m.match_id, m.Season, m.`Div`, m.Date, m.HomeTeam as h, m.AwayTeam as a, m.FTHG, m.FTAG, m.FTR  " + 
				"from matches as m " + 
				"where (m.HomeTeam=? and m.AwayTeam=?) or (m.AwayTeam=? and m.HomeTeam=?) ";
		
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, t1.getTeam());
			st.setString(2, t2.getTeam());
			st.setString(3, t1.getTeam());
			st.setString(4, t2.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(map.get(res.getString("h"))==null || map.get(res.getString("a"))==null)
					System.out.println("Errore team assente\n");
				
				else {
					
					result.add( new Match(
						res.getInt("match_id"), 
						null,
						//s, 
						res.getString("Div"),
						res.getDate("Date").toLocalDate(), map.get(res.getString("h")),
						map.get(res.getString("a")), res.getInt("FTHG"), res.getInt("FTAG"),
						res.getString("FTR"))) ;
				}
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public int getNumeroDiPartiteVinteTraDueTeam(Team t1, Team t2, TeamIdMap map){
		String sql ="select count(m.match_id) as cnt " + 
				"from matches as m " + 
				"where ( m.HomeTeam= ? and m.AwayTeam=? and m.FTR='H' ) or ( m.AwayTeam= ? and m.HomeTeam=? and m.FTR='A' )";
		int volte = 0;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, t1.getTeam());
			st.setString(2,t2.getTeam());
			st.setString(3, t1.getTeam());
			st.setString(4, t2.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				if(map.get(t1.getTeam())==null || map.get(t2.getTeam())==null)
					System.out.println("Errore team assente\n");
				
				else {
					volte = res.getInt("cnt");
				}
			}
			
			conn.close();
			return volte ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return volte;
		}	
	}
	
	public List<Match> getPartiteVinteInCasaDA(Team t1, TeamIdMap map, MatchIdMap mapM){
		String sql="select m.match_id, m.Season, m.`Div`, m.Date, m.HomeTeam as h, m.AwayTeam as a, m.FTHG, m.FTAG, m.FTR  "+
				"from matches as m " + 
				"where m.HomeTeam= ? and m.FTR='H' ";
		
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, t1.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Match m = mapM.get(res.getInt("match_id"));
				
				if(m==null){
					
					m= new Match(
						res.getInt("match_id"), 
						null,
						//s, 
						res.getString("Div"),
						res.getDate("Date").toLocalDate(), map.get(res.getString("h")),
						map.get(res.getString("a")), res.getInt("FTHG"), res.getInt("FTAG"),
						res.getString("FTR")) ;
					
					m= mapM.put(m);
				}
				result.add(m);
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}
