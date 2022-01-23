package jonnysod.football.control;

import jonnysod.football.model.Spiel;
import jonnysod.football.model.SpielSpieler;
import jonnysod.football.model.Spieler;
import jonnysod.football.statistic.SpielInfo;

import java.util.Date;

public class SpielHelper {

    Spiel spiel;

    public SpielHelper(Spiel spiel) {
        this.spiel = spiel;
    }

    public boolean removeFromHeim(Spieler spieler, Date now) {
        SpielInfo info = new SpielInfo(spiel);
        SpielSpieler spielSpieler = info.findSpielSpieler(spiel.getHeim(), spieler);
        if (!info.isBeendet()) {
            if (info.isStarted()) {
                spielSpieler.getAustrittsZeitpunktInSekunden().add(info.zeitpunktInSekunden(now));
                return true;
            } else {
                return spiel.getHeim().getSpielerList().remove(spielSpieler);
            }
        }
        return false;
    }
}
