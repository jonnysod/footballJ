package jonnysod.football.model;

import jonnysod.football.Utils;

import java.util.*;

public class Turnier extends ArrayList<Spieltag> {

	private String id;
	private String name;
	private TurnierTyp typ;
	private Date start;
	private final List<Team> teams = new ArrayList<>();

	public Turnier() {
	}

	public Turnier(String name, TurnierTyp typ, Date start, List<Team> teams) {
		this.name = name;
		this.typ = typ;
		this.start = start;
		this.teams.addAll(teams);
	}

	public Turnier(String id, Map<String, Object> export) {
		this.id = id;
		this.name = (String) export.get("name");
		this.typ = TurnierTyp.getTyp(((Number) export.get("typ")).intValue());
		this.start = Utils.dateFormExport(export.get("start"));
	}

	@Override
	public String toString() {
		return "Turnier "+name;
	}

	public Team findTeam(String id) {
		for (Team t : teams) {
			if (id.equals(t.getId())) {
				return t;
			}
		}
		return null;
	}

	public Spieltag findSpieltagForSpiel(Spiel s) {
		Spieltag spieltag = null;
		for (Spieltag st  : this) {
			if (st.contains(s)) {
				spieltag = st;
				break;
			}
		}
		return spieltag;
	}

	public List<Spiel> findAllSpiele() {
		List<Spiel> spiele = new ArrayList<>();
		for (Spieltag spieltag : this) {
			for (Spiel spiel : spieltag) {
				spiele.add(spiel);
			}
		}
		return spiele;
	}

	public Spieltag findSpieltag(String stId) {
		for (Spieltag st : this) {
			if (stId.equals(st.getId())) return st;
		}
		return null;
	}

	public Map<String, Object> exportBaseMap() {
		Map export = new HashMap<String,String>();
		export.put("name", name);
		export.put("typ", typ.getId());
		export.put("start", start.getTime());
		return export;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TurnierTyp getType() {
		return typ;
	}

	public void setType(TurnierTyp typ) {
		this.typ = typ;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public List<Team> getTeams() {
		return teams;
	}
}