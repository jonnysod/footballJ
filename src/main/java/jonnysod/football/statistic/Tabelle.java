package jonnysod.football.statistic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jonnysod.football.model.Spiel;
import jonnysod.football.model.Team;

public class Tabelle {

	public List<TeamTabellenDaten> teamTabellenDatenList = new ArrayList<TeamTabellenDaten>();
	Map<String, TeamTabellenDaten> teamTabellenDatenMap = new HashMap<>();

	public Tabelle(List<Spiel> spiele, Iterable<Team> teams) {
		super();
		for (Team team : teams) {
			TeamTabellenDaten teamInTabelle = new TeamTabellenDaten(team);
			teamTabellenDatenList.add(teamInTabelle);
			teamTabellenDatenMap.put(team.getId(), teamInTabelle);
		}
		for (Spiel spiel : spiele) {
			Team heim = spiel.getHeimTeam();
			addTabellenDaten(spiel, heim);
			Team auswaerts = spiel.getAuswaertsTeam();
			addTabellenDaten(spiel, auswaerts);
		}
		Collections.sort(teamTabellenDatenList);
	}

	protected void addTabellenDaten(Spiel spiel, Team team) {
		SpielInfo info = new SpielInfo(spiel);
		if (info.isStarted()) {
            TeamTabellenDaten tabellenDaten = teamTabellenDatenMap.get(team.getId());
            tabellenDaten.spiele += 1;
            tabellenDaten.punkte += info.punkte(team);
            tabellenDaten.torePlus += info.calcTore(team);
            tabellenDaten.toreMinus += info.calcGegentore(team);
            tabellenDaten.gewonnen += info.sieg(team) ? 1 : 0;
            tabellenDaten.unentschieden += info.unentschieden() ? 1 : 0;
            tabellenDaten.verloren += info.niederlage(team) ? 1 : 0;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder tabelle = new StringBuilder();
		tabelle.append("Team\t\t\t\tSp.\tg.\tu.\tv.\t\tTore\t\tDiff\t\tPkte.");
		for (TeamTabellenDaten teamInTabelle : teamTabellenDatenList) {
			tabelle.append("\n").append(teamInTabelle.toString());
		}
		return tabelle.toString();
	}



}
