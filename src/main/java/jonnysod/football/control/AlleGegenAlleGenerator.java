package jonnysod.football.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jonnysod.football.model.Spiel;
import jonnysod.football.model.Spieltag;
import jonnysod.football.model.Team;
import jonnysod.football.statistic.SpielInfo;

public class AlleGegenAlleGenerator implements SpieltagGenerator {

	@Override
	public Spieltag generateSpieltag(List<Team> teams,
			TurnierOptionen optionen) {
		Date start = new Date();
		Spieltag spieltag = new Spieltag(start);
		List<Spiel> spieles = new ArrayList<Spiel>();
		for (Team heim: teams) {
			for (Team auswaerts: teams) {
				if (heim != auswaerts) {
                    Spiel s = new Spiel(heim, auswaerts, optionen.spieldauerInSekunden);
					spieles.add(s);
				}
			}
		}
//		for (Spiel spiel : spieles) {
//			System.out.println(spiel.toString());// TODO log4j
//		}
//		System.out.println("------Spiele ordnen------------");
		List<Spiel> geordneteSpiele = sortSpiele(spieles, teams);
		spieltag.addAll(geordneteSpiele);
		return spieltag;
	}

	public List<Spiel> resortSpiele(List<Spiel> spiele, Collection<Team> teams) {
		if (teams.size()==3 && numBeendeteSpiele(spiele)==1
				|| teams.size()>3 && numBeendeteSpiele(spiele)==2 ) {
			return sortSpiele(spiele, teams);
		} else {
			return spiele;
		}
	}

	private int numBeendeteSpiele(List<Spiel> spiele) {
		int numBeendeteSpiele = 0;
		for (Spiel spiel : spiele) {
			if (new SpielInfo(spiel).isEnded()) {
				numBeendeteSpiele += 1;
			}
		}
		return numBeendeteSpiele;
	}

	private List<Spiel> sortSpiele(List<Spiel> spiele, Collection<Team> teams) {
		List<Spiel> geordneteSpiele = new ArrayList<Spiel>(spiele.size());
		List<TeamSpieleHistorie> teamSpieleHistorieList = new  ArrayList<TeamSpieleHistorie>(teams.size());
		for (Team team : teams) {
			teamSpieleHistorieList.add(new TeamSpieleHistorie(team));
		}
		Spiel erstesSpiel = spiele.remove(0);
		geordneteSpiele.add(erstesSpiel);
		updateTeamSpieleHistorie(erstesSpiel, null, teamSpieleHistorieList);
		Spiel leztesSpiel = erstesSpiel;
		while (!spiele.isEmpty()) {
			Spiel aktuellesSpiel = findNextSpiel(spiele, teamSpieleHistorieList);
			geordneteSpiele.add(aktuellesSpiel);
			spiele.remove(aktuellesSpiel);
			updateTeamSpieleHistorie(aktuellesSpiel, leztesSpiel, teamSpieleHistorieList);
			leztesSpiel = aktuellesSpiel;
		}
		return geordneteSpiele;
	}

	private void updateTeamSpieleHistorie(Spiel aktuellesSpiel, Spiel letztesSpiel,
			List<TeamSpieleHistorie> teamSpieleHistorieList) {
        SpielInfo aktlSpielInfo = new SpielInfo(aktuellesSpiel);
        SpielInfo letzSpielInfo = new SpielInfo(letztesSpiel);
		for (TeamSpieleHistorie teamSpieleHistorie : teamSpieleHistorieList) {
			Team team = teamSpieleHistorie.team;
			if (aktlSpielInfo.teilnehmer(team)) {
				teamSpieleHistorie.spiele += 1;
				if (aktlSpielInfo.teilnehmer(team)) {
					teamSpieleHistorie.spieleHeim += 1;
				}
				if (aktlSpielInfo.isEnded() && aktlSpielInfo.sieg(team)) {
					teamSpieleHistorie.spieleGewonnen += 1;
				}
				// wenn spiel erstesSpiel ist, dann ist letztesSpiel null
				if (letztesSpiel != null && letzSpielInfo.teilnehmer(team)) {
					teamSpieleHistorie.spieleInFolge += 1;
				}
			} else {
				teamSpieleHistorie.spieleInFolge = 0;
			}
		}
	}

	private Spiel findNextSpiel(List<Spiel> spiele, List<TeamSpieleHistorie> teamSpieleHistorieList) {
		Collections.sort(teamSpieleHistorieList);
		int teamIndex = 0;
		// solange noch zwei Teams ausgewaehlte werden koennen
		while (teamIndex < teamSpieleHistorieList.size() - 1) {
			Team naechtesTeam = teamSpieleHistorieList.get(teamIndex).team;
			// zuerst soll ein Heimspiel fuer nachstesTeam gefunden werden
			for (int i = teamIndex+1; i < teamSpieleHistorieList.size(); i++) {
				Team auswaerts = teamSpieleHistorieList.get(i).team;
				for (Spiel spiel : spiele) {
					if (spiel.getHeimTeam().equals(naechtesTeam)
						&& spiel.getAuswaertsTeam().equals(auswaerts)) {
							return spiel;
						}
				}
			}
			// sonst ein Auswaertsspiel fuer naechstesTeam
			for (int i = teamIndex+1; i < teamSpieleHistorieList.size(); i++) {
				Team heim = teamSpieleHistorieList.get(i).team;
				for (Spiel spiel : spiele) {
					if (spiel.getHeimTeam().equals(heim)
						&& spiel.getAuswaertsTeam().equals(naechtesTeam)) {
							return spiel;
						}
				}
			}
			teamIndex += 1;
		}
		// falls kein Spiel fuer naechstesTeam, das erste Spiel ausgeben
		return spiele.get(0);
	}

}
