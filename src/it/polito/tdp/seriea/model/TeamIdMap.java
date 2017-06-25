package it.polito.tdp.seriea.model;

import java.util.*;

public class TeamIdMap {
	private Map<String, Team> map;
	
	public TeamIdMap(){
		map = new HashMap<>();
	}
	
	public Team get(String id){
		return map.get(id);
	}
	
	public Team put(Team value){
	Team old = map.get(value);
		if(old == null){
			map.put(value.getTeam(), value);
			return value;
		} else 
			return old;
	}

}
