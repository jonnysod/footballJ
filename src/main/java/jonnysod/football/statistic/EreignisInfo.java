package jonnysod.football.statistic;

import jonnysod.football.model.*;

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

    public Team findMoeglicheVorlagengeber(SpielTeam spielTeam) {
        return getOtherSpieler(e.getSpieler(), spielTeam, "Vorlagengeber");
    }

    public Team findMoeglicheSchuetzen(SpielTeam spielTeam) {
        Ereignis vorlage = e.getFolgeEreignis(EreignisTyp.VORLAGE);
        Spieler vorlagenSpieler = vorlage == null? null : vorlage.getSpieler();
        return getOtherSpieler(vorlage.getSpieler(), spielTeam, "Schuetzen");
    }

    private Team getOtherSpieler(Spieler spieler, SpielTeam spielTeam, String teamName) {
        Team otherSpieler = new Team(teamName);
        for (SpielSpieler s  : spielTeam.getSpielerList()) {
            if (!s.equals(spieler)) {
                otherSpieler.getSpieler().add(s.getSpieler());
            }
        }
        return otherSpieler;
    }
}
