package jonnysod.football.statistic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jonnysod.football.model.Spiel;
import jonnysod.football.model.Ereignis;
import jonnysod.football.model.Spieler;
import jonnysod.football.model.Spieltag;
import jonnysod.football.model.Turnier;


public class ScorerListe {
	
	public List<SpielerStatistik> spielerStatistikList = new ArrayList<>();
	Map<Spieler, SpielerStatistik> spielerStatistikMap = new HashMap<>();

	public ScorerListe(List<Spiel> spiele, List<Spieler> allSpieler) {
		for (Spieler spieler : allSpieler) {
			SpielerStatistik statistik = new SpielerStatistik(spieler);
			spielerStatistikList.add(statistik);
			spielerStatistikMap.put(spieler, statistik);
		}
		for (Spiel spiel : spiele) {
			SpielInfo sInfo = new SpielInfo(spiel);
			if (sInfo.isStarted()) {
				List<Spieler> alleTeilnemer = sInfo.findAlleSpieler();
				for (Spieler teilnehmer : alleTeilnemer) {
					SpielerStatistik statistik = spielerStatistikMap.get(teilnehmer);
					statistik.addSpiel();
				}
			}
		}
		List<Ereignis> allEreignisse = gatherEreignisseWithSpieler(spiele);
		for (Ereignis spielEreignis : allEreignisse) {
			SpielerStatistik statistik = spielerStatistikMap.get(spielEreignis.getSpieler());
			statistik.addStatistic(spielEreignis);
		}
		Collections.sort(spielerStatistikList, new ScorerComparator());
		for (Iterator<SpielerStatistik> iterator = spielerStatistikList.iterator(); iterator.hasNext();) {
			SpielerStatistik s = iterator.next();
			if (s.getScorerpoints() == 0) {
				iterator.remove();
			}
		}
	}

	private List<Spiel> findSpiele(Turnier turnier) {
        List<Spiel> spiele = new ArrayList<>();
        for (Spieltag spieltag : turnier) {
            for (Spiel spiel : spieltag) {
                spiele.add(spiel);
            }
        }
        return spiele;
    }

    private List<Ereignis> gatherEreignisseWithSpieler(List<Spiel> spiele) {
		List<Ereignis> allEreignisse = new ArrayList<>();
		for (Spiel spiel : spiele) {
            SpielInfo sInfo = new SpielInfo(spiel);
			for (Ereignis spielEreignis : sInfo.getEreignisseUndFolgeEreignisse()) {
				if (spielEreignis.getSpieler() != null) {
					allEreignisse.add(spielEreignis);
				}
			}
		}
		return allEreignisse;
	}
	
	@Override
	public String toString() {
		StringBuilder scorerListe = new StringBuilder();
		scorerListe.append("Name\t\t\tTore\t\tVorlagen\t\tGesamt");
		Collections.sort(this.spielerStatistikList, new ScorerComparator());
		for (SpielerStatistik s : this.spielerStatistikList) {
			scorerListe.append("\n").append(s.toString());
		}
		return scorerListe.toString();
	}
	
	public String torschuetzenListe() {
		StringBuilder torschuetzenListe = new StringBuilder();
		torschuetzenListe.append("Name\t\t\tTore");
		Collections.sort(this.spielerStatistikList, new ToreComparator());
		Collections.reverse(spielerStatistikList);
		for (SpielerStatistik s : this.spielerStatistikList) {
			torschuetzenListe.append("\n").append(s.toreString());
		}
		return torschuetzenListe.toString();
	}

}
