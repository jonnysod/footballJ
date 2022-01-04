package jonnysod.football.model;

import java.io.Serializable;
import java.util.*;

public class Team implements Serializable {

	private String id;
	private String name;
	private List<Spieler> spielerList;

	public Team() {
	}

	public Team(String name) {
		this.name = name;
		this.spielerList = new ArrayList<>();
	}

	public Team(String id, String name) {
		this.id = id;
		this.name = name;
		this.spielerList = new ArrayList<>();
	}

	@Override
	public String toString() {
		if (this.name!=null) {
			return this.name;
		} else {
			return "Team";
		}
	}

	public String printTeamSpieler() {
		StringBuilder teamSpieler = new StringBuilder();
		teamSpieler.append(toString())
			.append(": ");
		for (Spieler s : getSpieler()) {
			teamSpieler.append(s.toString())
				.append(", ");
		}
		if (this.spielerList.size() > 0) {
			int length = teamSpieler.length();
			teamSpieler.delete(length-2, length);
		}
		return teamSpieler.toString();
	}

	public boolean isMember(Spieler spieler) {
		return spielerList.contains(spieler);
	}

	@Override
	public boolean equals(Object o) {
		if (this.id == null) {
			return this==o;
		}
		if (o instanceof Team) {
			Team otherTeam = (Team) o;
			if (this.id.equals(otherTeam.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (this.getId() == null) {
			return super.hashCode();
		}
		return this.getId().hashCode();
	}

//	public static List<Team> dummyTeams(int anzahl) {
//		List<Team> teams = new ArrayList<Team>(anzahl);
//		for (int i = 0; i < anzahl; i++) {
//			Team team = new Team("team " + i);
//			team.spieler.addAll(Spieler.createDummySpieler(null));
//			teams.add(team);
//		}
//		return teams;
//	}

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

	public List<Spieler> getSpieler() {
		return spielerList;
	}
}