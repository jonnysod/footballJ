package jonnysod.football.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpielSpieler implements Serializable {

	private Spieler spieler;
	private List<Integer> eintrittsZeitpunktInSekunden = new ArrayList<>();
	private List<Integer> austrittsZeitpunktInSekunden = new ArrayList<>();

	public SpielSpieler(Spieler spieler) {
		super();
		this.spieler = spieler;
	}

	public SpielSpieler(Spieler spieler, List<Integer> eintrittsZeitpunktInSekunden, List<Integer> austrittsZeitpunktInSekunden) {
		this.spieler = spieler;
		this.eintrittsZeitpunktInSekunden = eintrittsZeitpunktInSekunden;
		this.austrittsZeitpunktInSekunden = austrittsZeitpunktInSekunden;
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