package jonnysod.football.control;

import jonnysod.football.model.*;
import jonnysod.football.statistic.SpielInfo;
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
    private Spieler spielerTeam1;
    private Spieler spielerTeam2;
    private Date start;

    @BeforeEach
    void setUp() {
        Team team1 = new Team("Team1", "Team 1");
        Team team2 = new Team("Team2", "Team 2");
        spielerTeam1 = new Spieler("Spieler Team 1");
        team1.getSpieler().add(spielerTeam1);
        spielerTeam2 = new Spieler("Spieler Team 2");
        team2.getSpieler().add(spielerTeam2);
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
        Spieler spieler = new Spieler("Spieler 2");
        notStartedSpielHelper.addToHeim(spieler, start);
        SpielSpieler spielSpieler = notStartedSpielHelper.spiel.getHeim().getSpielerList().get(1);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add to started spiel
        // Add at start time
        startedSpielHelper.addToHeim(spieler, start);
        spielSpieler = startedSpielHelper.spiel.getHeim().getSpielerList().get(1);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add 5 seconds after start time
        int seconds = 5;
        Date afterSeconds = (Date) start.clone();
        afterSeconds.setSeconds(start.getSeconds() + seconds);
        startedSpielHelper.addToHeim(spieler, afterSeconds);
        spielSpieler = startedSpielHelper.spiel.getHeim().getSpielerList().get(1);
        assertEquals(Arrays.asList(seconds), spielSpieler.getEintrittsZeitpunktInSekunden());
    }

    @Test
    void addToAuswaerts() {
        // Add to not started spiel
        Spieler spieler = new Spieler("Spieler 2");
        notStartedSpielHelper.addToAuswaerts(spieler, start);
        SpielInfo info = new SpielInfo(notStartedSpielHelper.spiel);
        SpielSpieler spielSpieler = info.findSpielSpieler(notStartedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add to started spiel
        // Add at start time
        startedSpielHelper.addToAuswaerts(spieler, start);
        info = new SpielInfo(startedSpielHelper.spiel);
        spielSpieler = info.findSpielSpieler(startedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add 5 seconds after start time
        int seconds = 5;
        Date afterSeconds = (Date) start.clone();
        afterSeconds.setSeconds(start.getSeconds() + seconds);
        startedSpielHelper.addToAuswaerts(spieler, afterSeconds);
        info = new SpielInfo(startedSpielHelper.spiel);
        spielSpieler = info.findSpielSpieler(startedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(Arrays.asList(seconds), spielSpieler.getEintrittsZeitpunktInSekunden());
    }

    @Test
    void removeFromHeim() {
    }

    @Test
    void removeFromAuswaerts() {
        SpielSpieler spielSpieler = notStartedSpielHelper.spiel.getAuswaerts().getSpielerList().get(0);
        notStartedSpielHelper.removeFromAuswaerts(spielSpieler.getSpieler(), start);
        assertTrue(notStartedSpielHelper.spiel.getAuswaerts().getSpielerList().isEmpty());
    }
}