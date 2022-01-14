package jonnysod.football.statistic;

import jonnysod.football.model.Ereignis;
import jonnysod.football.model.EreignisTyp;
import jonnysod.football.model.Spieler;
import jonnysod.football.model.Team;

/**
 * Created by jonny on 19.07.15.
 */
public class EreignisInfo {
    private final Ereignis e;

    public EreignisInfo(Ereignis e) {
        this.e = e;
    }

    public String printZeitpunktInMinuten() {
        int minuten = e.getZeitpunktInSekunden() / 60;
        int sekunden = e.getZeitpunktInSekunden() % 60;
        String zeit = minuten + "'";
        if (sekunden < 10) {
            zeit += 0;
        }
        zeit += sekunden + "''";
        return zeit;
    }

    public Team findMoeglicheVorlagengeber() {
        if (e.getSpieler() == null) {
            return e.getTeam();
        } else {
            return getOtherSpieler(e.getSpieler(), "Vorlagengeber");
        }
    }

    public Team findMoeglicheSchuetzen() {
        Ereignis vorlage = e.getFolgeEreignis(EreignisTyp.VORLAGE);
        if (vorlage == null
                || vorlage.getSpieler() == null) {
            return e.getTeam();
        } else {
            return getOtherSpieler(vorlage.getSpieler(), "Schuetzen");
        }
    }

    private Team getOtherSpieler(Spieler spieler, String teamName) {
        Team otherSpieler = new Team(teamName);
        for (Spieler s  : e.getTeam().getSpieler()) {
            if (!s.getId().equals(spieler.getId())) {
                otherSpieler.getSpieler().add(s);
            }
        }
        return otherSpieler;
    }
}
