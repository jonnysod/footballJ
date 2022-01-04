package jonnysod.football.model;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class Turnier implements Serializable {

	private List<Spieltag> spieltags = new ArrayList<>();
	private String id;
	private String name;
	private TurnierTyp typ;
	private Long start;
	private List<Team> teams = new ArrayList<>();

	public Turnier() {
	}

	public Turnier(String name, TurnierTyp typ, Long start) {
		this.name = name;
		this.typ = typ;
		this.start = start;
	}

	public List<Spieltag> getSpieltags() {
		return spieltags;
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
		for (Spieltag st  : this.spieltags) {
			if (st.contains(s)) {
				spieltag = st;
				break;
			}
		}
		return spieltag;
	}

	public List<Spiel> findAllSpiele() {
		List<Spiel> spiele = new ArrayList<>();
		for (Spieltag spieltag : this.spieltags) {
			for (Spiel spiel : spieltag) {
				spiele.add(spiel);
			}
		}
		return spiele;
	}

	public Spieltag findSpieltag(String stId) {
		for (Spieltag st : this.spieltags) {
			if (stId.equals(st.getId())) return st;
		}
		return null;
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

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public List<Team> getTeams() {
		return teams;
	}

}