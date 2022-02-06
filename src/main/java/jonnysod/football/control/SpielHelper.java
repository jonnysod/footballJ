package jonnysod.football.control;

import jonnysod.football.model.Spiel;
import jonnysod.football.model.SpielSpieler;
import jonnysod.football.model.SpielTeam;
import jonnysod.football.model.Spieler;
import jonnysod.football.statistic.SpielInfo;

import java.util.List;

public class SpielHelper {

    Spiel spiel;

    public SpielHelper(Spiel spiel) {
        this.spiel = spiel;
    }

    public boolean addToHeim(Spieler spieler, Long now) {
        return addTo(spiel.getHeim(), spieler, now);
    }

    public boolean addToAuswaerts(Spieler spieler, Long now) {
        return addTo(spiel.getAuswaerts(), spieler, now);
    }

    private boolean addTo(SpielTeam team, Spieler spieler, Long now) {
        SpielInfo info = new SpielInfo(spiel);
        if (!info.isBeendet()) {
            SpielSpieler spielSpieler = info.findSpielSpieler(team, spieler);
            if (spielSpieler == null) {
                spielSpieler = new SpielSpieler(spieler);
                team.getSpielerList().add(spielSpieler);
            }
            if (info.isStarted() && !spiel.getStart().equals(now)) {
                int zeitpunktInSekunden = info.zeitpunktInSekunden(now);
                List<Integer> austrittsZeitpunktList = spielSpieler.getAustrittsZeitpunktInSekunden();
                int lastIndex = austrittsZeitpunktList.size() - 1;
                if (!austrittsZeitpunktList.isEmpty() && zeitpunktInSekunden == austrittsZeitpunktList.get(lastIndex)) {
                    austrittsZeitpunktList.remove(lastIndex);
                } else {
                    // By the way, this is the normal case but because of java couldn't put it in front
                    spielSpieler.getEintrittsZeitpunktInSekunden().add(zeitpunktInSekunden);
                }
            }
            return true;
        }
        return false;
    }

    public boolean removeFromHeim(Spieler spieler, Long now) {
        return removeFrom(spiel.getHeim(), spieler, now);
    }

    public boolean removeFromAuswaerts(Spieler spieler, Long now) {
        return removeFrom(spiel.getAuswaerts(), spieler, now);
    }

    private boolean removeFrom(SpielTeam team, Spieler spieler, Long now) {
        SpielInfo info = new SpielInfo(spiel);
        SpielSpieler spielSpieler = info.findSpielSpieler(team, spieler);
        if (!info.isBeendet()) {
            if (info.isStarted()) {
                int zeitpunktInSekunden = info.zeitpunktInSekunden(now);
                List<Integer> eintrittsZeitpunktList = spielSpieler.getEintrittsZeitpunktInSekunden();
                int lastIndex = eintrittsZeitpunktList.size() - 1;
                if (!eintrittsZeitpunktList.isEmpty() && zeitpunktInSekunden == eintrittsZeitpunktList.get(lastIndex)) {
                    eintrittsZeitpunktList.remove(lastIndex);
                } else {
                    // Again the normal case but I wasn't able to write it at top of if-else
                    spielSpieler.getAustrittsZeitpunktInSekunden().add(info.zeitpunktInSekunden(now));
                }
                return true;
            } else {
                return team.getSpielerList().remove(spielSpieler);
            }
        }
        return false;
    }
}
