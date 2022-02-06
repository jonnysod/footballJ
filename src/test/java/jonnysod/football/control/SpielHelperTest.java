package jonnysod.football.control;

import jonnysod.football.model.*;
import jonnysod.football.statistic.SpielInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
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
    private Instant start;

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
        start = Instant.now();
        spiel1.setStart(start.toEpochMilli());
        startedSpielHelper = new SpielHelper(spiel1);
        notStartedSpielHelper = new SpielHelper(spieltag.get(1));
    }

    @Test
    void addToHeim() {
        Spieler spieler = new Spieler("Spieler 2");
        notStartedSpielHelper.addToHeim(spieler, start.toEpochMilli());
        SpielInfo info = new SpielInfo(notStartedSpielHelper.spiel);
        SpielSpieler notStartedSpielSpieler = info.findSpielSpieler(notStartedSpielHelper.spiel.getHeim(), spieler);
        assertEquals(new ArrayList<>(), notStartedSpielSpieler.getEintrittsZeitpunktInSekunden());

        // Add to started spiel
        // Add at start time
        startedSpielHelper.addToHeim(spieler, start.toEpochMilli());
        info = new SpielInfo(startedSpielHelper.spiel);
        assertEquals(new ArrayList<>(), notStartedSpielSpieler.getEintrittsZeitpunktInSekunden());

        // Add 5 seconds after start time
        int seconds = 5;
        Instant after5Seconds = start.plusSeconds(seconds);
        startedSpielHelper.addToHeim(spieler, after5Seconds.toEpochMilli());
        SpielSpieler startedSpielSpieler = info.findSpielSpieler(startedSpielHelper.spiel.getHeim(), spieler);
        assertEquals(Arrays.asList(seconds), startedSpielSpieler.getEintrittsZeitpunktInSekunden());

        // Remove and add 10 seconds after start time
        Instant after10Seconds = after5Seconds.plusSeconds(seconds);
        startedSpielHelper.removeFromHeim(spieler, after10Seconds.toEpochMilli());
        assertEquals(Arrays.asList(seconds+seconds), startedSpielSpieler.getAustrittsZeitpunktInSekunden());
        startedSpielHelper.addToHeim(spieler, after10Seconds.toEpochMilli());
        assertEquals(Arrays.asList(seconds), startedSpielSpieler.getEintrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(), startedSpielSpieler.getAustrittsZeitpunktInSekunden());

        // Remove after 10 and add after 15 seconds
        startedSpielHelper.removeFromHeim(spieler, after10Seconds.toEpochMilli());
        assertEquals(Arrays.asList(seconds+seconds), startedSpielSpieler.getAustrittsZeitpunktInSekunden());
        // 15 seconds
        Instant after15Seconds = after10Seconds.plusSeconds(seconds);

        startedSpielHelper.addToHeim(spieler, after15Seconds.toEpochMilli());
        assertEquals(Arrays.asList(seconds, 3 * seconds), startedSpielSpieler.getEintrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(2 * seconds), startedSpielSpieler.getAustrittsZeitpunktInSekunden());
    }

    @Test
    void addToAuswaerts() {
        // Add to not started spiel
        Spieler spieler = new Spieler("Spieler 2");
        notStartedSpielHelper.addToAuswaerts(spieler, start.toEpochMilli());
        SpielInfo info = new SpielInfo(notStartedSpielHelper.spiel);
        SpielSpieler spielSpieler = info.findSpielSpieler(notStartedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add to started spiel
        // Add at start time
        startedSpielHelper.addToAuswaerts(spieler, start.toEpochMilli());
        info = new SpielInfo(startedSpielHelper.spiel);
        spielSpieler = info.findSpielSpieler(startedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(new ArrayList<>(), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add 5 seconds after start time
        int seconds = 5;
        Instant after5Seconds = start.plusSeconds(seconds);
        startedSpielHelper.addToAuswaerts(spieler, after5Seconds.toEpochMilli());
        info = new SpielInfo(startedSpielHelper.spiel);
        spielSpieler = info.findSpielSpieler(startedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(Arrays.asList(seconds), spielSpieler.getEintrittsZeitpunktInSekunden());
    }

    @Test
    void removeFromHeim() {
        SpielSpieler spielSpieler = notStartedSpielHelper.spiel.getHeim().getSpielerList().get(0);
        notStartedSpielHelper.removeFromHeim(spielSpieler.getSpieler(), start.toEpochMilli());
        assertTrue(notStartedSpielHelper.spiel.getHeim().getSpielerList().isEmpty());

        SpielSpieler spielSpielerStarted = startedSpielHelper.spiel.getHeim().getSpielerList().get(0);
        startedSpielHelper.removeFromHeim(spielSpielerStarted.getSpieler(), start.toEpochMilli());
        assertTrue(notStartedSpielHelper.spiel.getHeim().getSpielerList().isEmpty());

        // Add at start time and remove 5 seconds after start time
        Spieler spieler3 = new Spieler("Spieler 3");
        startedSpielHelper.addToHeim(spieler3, start.toEpochMilli());
        int seconds = 5;
        Instant after5Seconds = start.plusSeconds(seconds);
        startedSpielHelper.removeFromHeim(spieler3, after5Seconds.toEpochMilli());
        SpielInfo info = new SpielInfo(startedSpielHelper.spiel);
        SpielSpieler spielSpieler3 = info.findSpielSpieler(startedSpielHelper.spiel.getHeim(), spieler3);
        assertEquals(Arrays.asList(seconds), spielSpieler3.getAustrittsZeitpunktInSekunden());

        // Add and remove 5 seconds after start time
        Spieler spieler4 = new Spieler("Spieler 4");
        startedSpielHelper.addToHeim(spieler4, after5Seconds.toEpochMilli());
        startedSpielHelper.removeFromHeim(spieler4, after5Seconds.toEpochMilli());
        SpielSpieler spielSpieler4 = info.findSpielSpieler(startedSpielHelper.spiel.getHeim(), spieler4);
        assertEquals(new ArrayList<>(), spielSpieler4.getAustrittsZeitpunktInSekunden());

        // Add after 5 and remove after 10 seconds
        startedSpielHelper.addToHeim(spieler4, after5Seconds.toEpochMilli());
        Instant after10Seconds = after5Seconds.plusSeconds(seconds);
        startedSpielHelper.removeFromHeim(spieler4, after10Seconds.toEpochMilli());
        assertEquals(Arrays.asList(2 * seconds), spielSpieler4.getAustrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(seconds), spielSpieler4.getEintrittsZeitpunktInSekunden());
    }

    @Test
    void removeFromAuswaerts() {
        SpielSpieler spielSpieler = notStartedSpielHelper.spiel.getAuswaerts().getSpielerList().get(0);
        notStartedSpielHelper.removeFromAuswaerts(spielSpieler.getSpieler(), start.toEpochMilli());
        assertTrue(notStartedSpielHelper.spiel.getAuswaerts().getSpielerList().isEmpty());
    }
}