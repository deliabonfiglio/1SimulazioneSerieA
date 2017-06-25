package it.polito.tdp.seriea.model;

import java.time.LocalDate;

public class BigMatch {
	private int id ;
	private Season season ;
	private String div ;
	private LocalDate date ;
	private Team homeTeam ;
	private Team awayTeam ;
	private int fthg ; // full time home goals
	private int ftag ; // full time away goals
	private String ftr ; // full time result (H, A, D)
	// è possibile aggiungere altri campi, se risulteranno necessari
	
	private int hthg;
	private int htag;
	private String htr;
	private int hs;
	private int as;
	private int hst;
	private int ast;
	private int hf;
	private int af;
	private int hc;
	private int ac;
	private int hy;
	private int ay;
	private int hR;
	private int aR;
	/**
	 * New match
	 * 
	 * @param id
	 * @param season
	 * @param div
	 * @param date
	 * @param homeTeam
	 * @param awayTeam
	 * @param fthg
	 * @param ftag
	 * @param ftr
	 */
	
	public BigMatch(int id, Season season, String div, LocalDate date, Team homeTeam, Team awayTeam, int fthg, int ftag,
			String ftr, int hthg, int htag, String htr, int hs, int as, int hst, int ast, int hc, int ac, int hf, int af,
			int hY, int aY, int hR, int aR) {
		super();
		this.id = id;
		this.season = season;
		this.div = div;
		this.date = date;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.fthg = fthg;
		this.ftag = ftag;
		this.ftr = ftr;
		this.hthg = hthg;
		this.htag = htag;
		this.htr = htr;
		this.hs = hs;
		this.as = as;
		this.hst = hst;
		this.ast = ast;
		this.hc = hc;
		this.ac = ac;
		this.hf = hf;
		this.af = af;
		this.hy = hY;
		this.ay = aY;
		this.hR = hR;
		this.aR = aR;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BigMatch other = (BigMatch) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	

	@Override
	public String toString() {
		return "BigMatch [id=" + id + ", season=" + season + ", div=" + div + ", date=" + date + ", homeTeam="
				+ homeTeam + ", awayTeam=" + awayTeam + ", fthg=" + fthg + ", ftag=" + ftag + ", ftr=" + ftr + ", hthg="
				+ hthg + ", htag=" + htag + ", htr=" + htr + ", hs=" + hs + ", as=" + as + ", hst=" + hst + ", ast="
				+ ast + ", hf=" + hf + ", af=" + af + ", hc=" + hc + ", ac=" + ac + ", hy=" + hy + ", ay=" + ay
				+ ", hR=" + hR + ", aR=" + aR + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
}
