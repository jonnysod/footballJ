package jonnysod.football.statistic;

import jonnysod.football.model.Spiel;
import jonnysod.football.model.Spieler;
import jonnysod.football.model.Spieltag;
import jonnysod.football.model.Team;
import jonnysod.football.model.Turnier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonny on 05.08.15.
 */
public class TurnierInfo {
    Turnier t;

    public TurnierInfo(Turnier turnier) {
        t = turnier;
    }

    public List<Spieler> findAllSpieler() {
        List<Spieler> allSpieler = new ArrayList<Spieler>();
        for (Team team : t.getTeams()) {
            allSpieler.addAll(team);
        }
        return allSpieler;
    }

    public List<Spiel> findAllSpiele() {
        List<Spiel> spiele = new ArrayList<>();
        for (Spieltag spieltag : t) {
            for (Spiel spiel : spieltag) {
                spiele.add(spiel);
            }
        }
        return spiele;
    }

    public Spiel findLastSpiel() {
        int lastSpieltagPos = t.size() - 1;
        Spieltag lastSt = t.get(lastSpieltagPos);
        int lastSpielPos = lastSt.size() - 1;
        return lastSt.get(lastSpielPos);
    }
}
