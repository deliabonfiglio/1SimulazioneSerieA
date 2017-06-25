package it.polito.tdp.seriea.model;

import java.util.*;

public class MatchIdMap {
	private Map<Integer, Match>map;

	public MatchIdMap() {
		super();
		this.map = new HashMap<>();
	}
	
	public Match get(int id){
		return map.get(id);
	}
	
	public Match put(Match value){
		Match old = map.get(value);
		if(old == null){
			map.put(value.getId(), value);
			return value;
		} else 
			return old;
	}


}
