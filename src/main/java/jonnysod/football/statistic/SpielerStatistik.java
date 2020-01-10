package jonnysod.football.statistic;

import jonnysod.football.model.Ereignis;
import jonnysod.football.model.Spieler;

public class SpielerStatistik {

	private Spieler spieler;
	private int spiele = 0;
	private int tore = 0;
	private int vorlagen = 0;
	private int gelbeKarten = 0;
	private int roteKarten = 0;

	public SpielerStatistik(Spieler spieler) {
		this.spieler = spieler;
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public int getSpiele() {
		return spiele;
	}

	public int getTore() {
		return tore;
	}

	public int getVorlagen() {
		return vorlagen;
	}

	public int getGelbeKarten() {
		return gelbeKarten;
	}

	public int getRoteKarten() {
		return roteKarten;
	}

	public int getScorerpoints() {
		return tore + vorlagen;
	}

	@Override
	public String toString() {
		String spielerStatistik = "%-10s%3d\t\t\t\t%3d\t\t\t\t%3d";
		return String.format(spielerStatistik, spieler.getName(), tore, vorlagen, tore+vorlagen);
	}
	
	public String toreString() {
		String spielerTore = "%s\t\t\t%3d";
		return String.format(spielerTore, spieler.getName(), tore);
	}

	public void addStatistic(Ereignis e) {
		switch (e.getTyp()) {
			case TOR:
				this.tore += 1;
				break;
			case VORLAGE:
				this.vorlagen += 1;
				break;
			default:
				break;
		}
	}

	public void addSpiel() {
		this.spiele += 1;
	}
}
