package jonnysod.football.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpielSpieler implements Serializable {

	private Spieler spieler;
	private List<Integer> eintrittsZeitpunktInSekunden = new ArrayList<>();
	private List<Integer> austrittsZeitpunktInSekunden = new ArrayList<>();

	public SpielSpieler(Spieler spieler) {
		this(spieler, 0);
	}

	public SpielSpieler(Spieler spieler, int eintrittInSekunden) {
		super();
		this.spieler = spieler;
		this.eintrittsZeitpunktInSekunden.add(eintrittInSekunden);
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public List<Integer> getEintrittsZeitpunktInSekunden() {
		return eintrittsZeitpunktInSekunden;
	}

	public List<Integer> getAustrittsZeitpunktInSekunden() {
		return austrittsZeitpunktInSekunden;
	}

	public String getId() {
		return spieler.getId();
	}

	@Override
	public String toString() {
		return spieler.getName();
	}

	@Override
	public boolean equals(Object o) {
		if (spieler.getId() == null) {
			return super.equals(o);
		}
		if (o instanceof SpielSpieler) {
			SpielSpieler otherSpieler = (SpielSpieler) o;
			if (spieler.getId().equals(otherSpieler.getSpieler().getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
	    return spieler.hashCode();
	}

}