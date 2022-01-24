package jonnysod.football.control;

import jonnysod.football.model.Spiel;
import jonnysod.football.model.SpielSpieler;
import jonnysod.football.model.SpielTeam;
import jonnysod.football.model.Spieler;
import jonnysod.football.statistic.SpielInfo;

import java.util.Date;

public class SpielHelper {

    Spiel spiel;

    public SpielHelper(Spiel spiel) {
        this.spiel = spiel;
    }

    public boolean addToHeim(Spieler spieler, Date now) {
        return addTo(spiel.getHeim(), spieler, now);
    }

    public boolean addToAuswaerts(Spieler spieler, Date now) {
        return addTo(spiel.getHeim(), spieler, now);
    }

    private boolean addTo(SpielTeam team, Spieler spieler, Date now) {
        SpielInfo info = new SpielInfo(spiel);
        SpielSpieler spielSpieler = info.findSpielSpieler(team, spieler);
        if (spielSpieler == null) {
            spielSpieler = new SpielSpieler(spieler);
        }
        if (!info.isBeendet()) {
            if (info.isStarted()) {
                spielSpieler.getEintrittsZeitpunktInSekunden().add(info.zeitpunktInSekunden(now));
                return true;
            } else {
                return team.getSpielerList().add(spielSpieler);
            }
        }
        return false;
    }

    public boolean removeFromHeim(Spieler spieler, Date now) {
        return removeFrom(spiel.getHeim(), spieler, now);
    }

    public boolean removeFromAuswaerts(Spieler spieler, Date now) {
        return removeFrom(spiel.getHeim(), spieler, now);
    }

    private boolean removeFrom(SpielTeam team, Spieler spieler, Date now) {
        SpielInfo info = new SpielInfo(spiel);
        SpielSpieler spielSpieler = info.findSpielSpieler(team, spieler);
        if (!info.isBeendet()) {
            if (info.isStarted()) {
                spielSpieler.getAustrittsZeitpunktInSekunden().add(info.zeitpunktInSekunden(now));
                return true;
            } else {
                return team.getSpielerList().remove(spielSpieler);
            }
        }
        return false;
    }
}
