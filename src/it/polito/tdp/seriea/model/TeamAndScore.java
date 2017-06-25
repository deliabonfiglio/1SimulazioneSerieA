package it.polito.tdp.seriea.model;

public class TeamAndScore implements Comparable<TeamAndScore>{
	private Team team;
	private int score;
	
	public TeamAndScore(Team team, int score) {
		super();
		this.team = team;
		this.score = score;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public int compareTo(TeamAndScore altro) {
		return -(this.getScore()-altro.getScore());
	}
	
	public String toString(){
		return team.getTeam()+" punti: "+score+"\n";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + score;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamAndScore other = (TeamAndScore) obj;
		if (score != other.score)
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	
	

}
