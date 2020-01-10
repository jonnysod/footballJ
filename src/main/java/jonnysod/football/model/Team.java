package jonnysod.football.model;

import java.util.*;

public class Team extends ArrayList<Spieler> {

	private String id;
	private String name;

	public Team() {
	}

	public Team(String name) {
		this.name = name;
	}

	public Team(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Team(String id, Map<String, Object> export) {
		this.id = id;
		this.name = (String) export.get("name");
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
		for (Spieler s : this) {
			teamSpieler.append(s.toString())
				.append(", ");
		}
		if (this.size() > 0) {
			int length = teamSpieler.length();
			teamSpieler.delete(length-2, length);
		}
		return teamSpieler.toString();
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

	public Map<String, Object> exportBaseMap() {
		Map<String, Object> export = new HashMap<>();
		export.put("name", this.name);
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
}