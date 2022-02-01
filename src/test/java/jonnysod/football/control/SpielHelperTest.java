package jonnysod.football.control;

import jonnysod.football.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpielHelperTest {

    private SpielHelper startedSpielHelper;
    private SpielHelper notStartedSpielHelper;
    private Date start;

    @BeforeEach
    void setUp() {
        Team team1 = new Team("Team1", "Team 1");
        Team team2 = new Team("Team2", "Team 2");
        List<Team> teamList = Arrays.asList(team1, team2);
        SpieltagGenerator generator = new AlleGegenAlleGenerator();
        TurnierOptionen optionen = new TurnierOptionen(60);
        Spieltag spieltag = generator.generateSpieltag(teamList, optionen);
        Spiel spiel1 = spieltag.get(0);
        start = new Date();
        spiel1.setStart(start);
        startedSpielHelper = new SpielHelper(spiel1);
        notStartedSpielHelper = new SpielHelper(spieltag.get(1));
    }

    @Test
    void addToHeim() {
        Spieler spieler = new Spieler("Spieler 1");
        notStartedSpielHelper.addToHeim(spieler, start);
        SpielSpieler spielSpieler = notStartedSpielHelper.spiel.getHeim().getSpielerList().get(0);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add to started spiel
        // Add at start time
        startedSpielHelper.addToHeim(spieler, start);
        spielSpieler = startedSpielHelper.spiel.getHeim().getSpielerList().get(0);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add 5 seconds after start time
        int seconds = 5;
        Date afterSeconds = (Date) start.clone();
        afterSeconds.setSeconds(start.getSeconds() + seconds);
        startedSpielHelper.addToHeim(spieler, afterSeconds);
        spielSpieler = startedSpielHelper.spiel.getHeim().getSpielerList().get(0);
        assertEquals(Arrays.asList(seconds), spielSpieler.getEintrittsZeitpunktInSekunden());
    }

    @Test
    void addToAuswaerts() {
        Spieler spieler = new Spieler("Spieler 1");
        notStartedSpielHelper.addToAuswaerts(spieler, start);
        SpielSpieler spielSpieler = notStartedSpielHelper.spiel.getAuswaerts().getSpielerList().get(0);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add to started spiel
        // Add at start time
        startedSpielHelper.addToAuswaerts(spieler, start);
        spielSpieler = startedSpielHelper.spiel.getAuswaerts().getSpielerList().get(0);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add 5 seconds after start time
        int seconds = 5;
        Date afterSeconds = (Date) start.clone();
        afterSeconds.setSeconds(start.getSeconds() + seconds);
        startedSpielHelper.addToAuswaerts(spieler, afterSeconds);
        spielSpieler = startedSpielHelper.spiel.getAuswaerts().getSpielerList().get(0);
        assertEquals(Arrays.asList(seconds), spielSpieler.getEintrittsZeitpunktInSekunden());
    }

    @Test
    void removeFromHeim() {
    }

    @Test
    void removeFromAuswaerts() {
    }
}