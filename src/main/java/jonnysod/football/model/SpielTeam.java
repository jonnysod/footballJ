package jonnysod.football.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpielTeam implements Serializable {
    private List<SpielSpieler> spielerList;
    private Team team;

    public SpielTeam(Team team) {
        this.team = team;
        spielerList = new ArrayList<>();
        team.getSpieler();
        for (Spieler spieler : team.getSpieler()) {
            SpielSpieler spielSpieler = new SpielSpieler(spieler);
            spielSpieler.getEintrittsZeitpunktInSekunden().add(0);
            spielerList.add(spielSpieler);
        }
    }

    public SpielTeam(List<SpielSpieler> spielerList, Team team) {
        this.spielerList = spielerList;
        this.team = team;
    }

    public List<SpielSpieler> getSpielerList() {
        return spielerList;
    }

    public List<Spieler> getSpieler() {
        List<Spieler> spieler = new ArrayList<>(spielerList.size());
        for (SpielSpieler s: getSpielerList()) {
            spieler.add(s.getSpieler());
        }
        return spieler;
    }

    public Team getTeam() {
        return team;
    }

    public String getId() {
        return team.getId();
    }

    public String getName() {
        return team.getName();
    }
}
