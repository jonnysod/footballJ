package jonnysod.football.model;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class Turnier implements Serializable, Iterable<Spieltag> {

	private List<Spieltag> spieltags = new ArrayList<>();
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

	public int size() {
		return spieltags.size();
	}

	public boolean add(Spieltag spieltag) {
		return this.spieltags.add(spieltag);
	}

	public boolean remove(Object o) {
		return spieltags.remove(o);
	}

	public Spieltag get(int i) {
		return spieltags.get(i);
	}

	public void add(int i, Spieltag spieltag) {
		spieltags.add(i, spieltag);
	}

	public boolean remove(Spieltag spieltag) {
		return this.spieltags.remove(spieltag);
	}

	public Spieltag remove(int index) {
		return this.spieltags.remove(index);
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

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public List<Team> getTeams() {
		return teams;
	}

	@Override
	public Iterator<Spieltag> iterator() {
		return spieltags.iterator();
	}

	@Override
	public void forEach(Consumer<? super Spieltag> action) {

	}

	@Override
	public Spliterator<Spieltag> spliterator() {
		return null;
	}
}