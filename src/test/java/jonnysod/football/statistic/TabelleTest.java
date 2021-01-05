package jonnysod.football.statistic;

import jonnysod.football.control.AlleGegenAlleGenerator;
import jonnysod.football.control.TurnierOptionen;
import jonnysod.football.model.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;


public class TabelleTest {

    @Test
    public void orderTest() {
        Team team1 = new Team("1", "Team 1");
        Team team2 = new Team("2", "Team 2");
        Team team3 = new Team("3", "Team 3");
        List<Team> teams = Arrays.asList(team1, team2, team3);
        Spieltag spieltag = new AlleGegenAlleGenerator().generateSpieltag(
                teams,
                new TurnierOptionen(10)
        );

        for (Spiel spiel : spieltag) {
            if (new SpielInfo(spiel).teilnehmer(team1)) {
                Ereignis tor = new Ereignis(EreignisTyp.TOR, 0, new Date());
                tor.setTeam(team1);
                spiel.add(tor);
            } else {
                Ereignis tor = new Ereignis(EreignisTyp.TOR, 0, new Date());
                tor.setTeam(team2);
                spiel.add(tor);
            }
        }

        Tabelle tabelle = new Tabelle(spieltag.getSpiels(), teams);
        assertSame(team1 , tabelle.teamTabellenDatenList.get(0).team);
        assertSame(team3 , tabelle.teamTabellenDatenList.get(2).team);

    }
}
