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
        assertEquals(Arrays.asList(0), notStartedSpielSpieler.getEintrittsZeitpunktInSekunden());

        // Add to started spiel
        // Add at start time
        startedSpielHelper.addToHeim(spieler, start.toEpochMilli());
        info = new SpielInfo(startedSpielHelper.spiel);
        assertEquals(Arrays.asList(0), notStartedSpielSpieler.getEintrittsZeitpunktInSekunden());

        // Add 5 seconds after start time, but already added
        Instant after5Seconds = start.plusSeconds(5);
        boolean wasAdded = startedSpielHelper.addToHeim(spieler, after5Seconds.toEpochMilli());
        SpielSpieler startedSpielSpieler = info.findSpielSpieler(startedSpielHelper.spiel.getHeim(), spieler);
        assertEquals(Arrays.asList(0), startedSpielSpieler.getEintrittsZeitpunktInSekunden());

        // Remove and add 10 seconds after start time
        Instant after10Seconds = after5Seconds.plusSeconds(5);
        startedSpielHelper.removeFromHeim(spieler, after10Seconds.toEpochMilli());
        assertEquals(Arrays.asList(10), startedSpielSpieler.getAustrittsZeitpunktInSekunden());
        startedSpielHelper.addToHeim(spieler, after10Seconds.toEpochMilli());
        assertEquals(Arrays.asList(0), startedSpielSpieler.getEintrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(), startedSpielSpieler.getAustrittsZeitpunktInSekunden());

        // Remove after 5 and add after 10
        startedSpielHelper.removeFromHeim(spieler, after5Seconds.toEpochMilli());
        assertEquals(Arrays.asList(5), startedSpielSpieler.getAustrittsZeitpunktInSekunden());
        startedSpielHelper.addToHeim(spieler, after10Seconds.toEpochMilli());
        assertEquals(Arrays.asList(0, 10), startedSpielSpieler.getEintrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(5), startedSpielSpieler.getAustrittsZeitpunktInSekunden());

        // Remove after 15 and add after 15 seconds
        Instant after15Seconds = after10Seconds.plusSeconds(5);
        startedSpielHelper.removeFromHeim(spieler, after15Seconds.toEpochMilli());
        assertEquals(Arrays.asList(5, 15), startedSpielSpieler.getAustrittsZeitpunktInSekunden());
        startedSpielHelper.addToHeim(spieler, after15Seconds.toEpochMilli());
        assertEquals(Arrays.asList(0, 10), startedSpielSpieler.getEintrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(5), startedSpielSpieler.getAustrittsZeitpunktInSekunden());

        // Now try to add after 5, this should fail because eintritt austritt seconds already beyond
        boolean wasSuccessful = startedSpielHelper.addToHeim(spieler, after5Seconds.toEpochMilli());
        assertFalse(wasSuccessful);
        assertEquals(Arrays.asList(0, 10), startedSpielSpieler.getEintrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(5), startedSpielSpieler.getAustrittsZeitpunktInSekunden());
    }

    @Test
    void addToAuswaerts() {
        // Add to not started spiel
        Spieler spieler = new Spieler("Spieler 2");
        notStartedSpielHelper.addToAuswaerts(spieler, start.toEpochMilli());
        SpielInfo info = new SpielInfo(notStartedSpielHelper.spiel);
        SpielSpieler spielSpieler = info.findSpielSpieler(notStartedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(Arrays.asList(0), spielSpieler.getEintrittsZeitpunktInSekunden());
        // Add to started spiel
        // Add at start time
        startedSpielHelper.addToAuswaerts(spieler, start.toEpochMilli());
        info = new SpielInfo(startedSpielHelper.spiel);
        spielSpieler = info.findSpielSpieler(startedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(Arrays.asList(0), spielSpieler.getEintrittsZeitpunktInSekunden());
        startedSpielHelper.removeFromAuswaerts(spieler, start.toEpochMilli());
        // Add 5 seconds after start time
        int seconds = 5;
        Instant after5Seconds = start.plusSeconds(seconds);
        startedSpielHelper.addToAuswaerts(spieler, after5Seconds.toEpochMilli());
        spielSpieler = info.findSpielSpieler(startedSpielHelper.spiel.getAuswaerts(), spieler);
        assertEquals(Arrays.asList(5), spielSpieler.getEintrittsZeitpunktInSekunden());
    }

    @Test
    void removeFromHeim() {
        SpielSpieler spielSpieler = notStartedSpielHelper.spiel.getHeim().getSpielerList().get(0);
        notStartedSpielHelper.removeFromHeim(spielSpieler.getSpieler(), start.toEpochMilli());
        assertTrue(notStartedSpielHelper.spiel.getHeim().getSpielerList().isEmpty());

        SpielSpieler spielSpielerStarted = startedSpielHelper.spiel.getHeim().getSpielerList().get(0);
        startedSpielHelper.removeFromHeim(spielSpielerStarted.getSpieler(), start.toEpochMilli());
        assertTrue(notStartedSpielHelper.spiel.getHeim().getSpielerList().isEmpty());

        // Add at start time and remove at start time
        Spieler spieler3 = new Spieler("Spieler 3");
        startedSpielHelper.addToHeim(spieler3, start.toEpochMilli());
        SpielInfo info = new SpielInfo(startedSpielHelper.spiel);
        SpielSpieler spielSpieler3 = info.findSpielSpieler(startedSpielHelper.spiel.getHeim(), spieler3);
        assertTrue(startedSpielHelper.spiel.getHeim().getSpielerList().contains(spielSpieler3));
        startedSpielHelper.removeFromHeim(spieler3, start.toEpochMilli());
        assertFalse(startedSpielHelper.spiel.getHeim().getSpielerList().contains(spielSpieler3));

        // Add at start time and remove 5 seconds after start time
        startedSpielHelper.addToHeim(spieler3, start.toEpochMilli());
        int seconds = 5;
        Instant after5Seconds = start.plusSeconds(seconds);
        startedSpielHelper.removeFromHeim(spieler3, after5Seconds.toEpochMilli());
        info = new SpielInfo(startedSpielHelper.spiel);
        spielSpieler3 = info.findSpielSpieler(startedSpielHelper.spiel.getHeim(), spieler3);
        assertEquals(Arrays.asList(seconds), spielSpieler3.getAustrittsZeitpunktInSekunden());

        // Add and remove 5 seconds after start time
        Spieler spieler4 = new Spieler("Spieler 4");
        startedSpielHelper.addToHeim(spieler4, after5Seconds.toEpochMilli());
        startedSpielHelper.removeFromHeim(spieler4, after5Seconds.toEpochMilli());
        assertFalse(startedSpielHelper.spiel.getHeim().getSpielerList().contains(spieler4));

        // Add after 5 and remove after 10 seconds
        startedSpielHelper.addToHeim(spieler4, after5Seconds.toEpochMilli());
        Instant after10Seconds = after5Seconds.plusSeconds(seconds);
        startedSpielHelper.removeFromHeim(spieler4, after10Seconds.toEpochMilli());
        info = new SpielInfo(startedSpielHelper.spiel);
        SpielSpieler spielSpieler4 = info.findSpielSpieler(startedSpielHelper.spiel.getHeim(), spieler4);
        assertEquals(Arrays.asList(2 * seconds), spielSpieler4.getAustrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(seconds), spielSpieler4.getEintrittsZeitpunktInSekunden());

        // Add after 20 and remove after 15 seconds
        Instant after15Seconds = after10Seconds.plusSeconds(seconds);
        Instant after20Seconds = after15Seconds.plusSeconds(seconds);
        startedSpielHelper.addToHeim(spieler4, after20Seconds.toEpochMilli());
        boolean wasRemoved = startedSpielHelper.removeFromHeim(spieler4, after15Seconds.toEpochMilli());
        assertTrue(wasRemoved);
        assertEquals(Arrays.asList(2 * seconds), spielSpieler4.getAustrittsZeitpunktInSekunden());

        // Add after 15 and remove after 8 seconds (i.e. remove before last removal at 10th second)
        Instant after8Seconds = start.plusSeconds(8);
        startedSpielHelper.addToHeim(spieler4, after15Seconds.toEpochMilli());
        assertFalse(startedSpielHelper.removeFromHeim(spieler4, after8Seconds.toEpochMilli()));
        assertEquals(Arrays.asList(2 * seconds), spielSpieler4.getAustrittsZeitpunktInSekunden());
        assertEquals(Arrays.asList(seconds, 3 * seconds), spielSpieler4.getEintrittsZeitpunktInSekunden());
    }

    @Test
    void removeFromAuswaerts() {
        SpielSpieler spielSpieler = notStartedSpielHelper.spiel.getAuswaerts().getSpielerList().get(0);
        notStartedSpielHelper.removeFromAuswaerts(spielSpieler.getSpieler(), start.toEpochMilli());
        assertTrue(notStartedSpielHelper.spiel.getAuswaerts().getSpielerList().isEmpty());
    }

    @Test
    void findCurrentHeimTeam() {
        Spieler spieler2 = new Spieler("Spieler 2");
        startedSpielHelper.addToHeim(spieler2, start.toEpochMilli());

        Spieler spieler3 = new Spieler("Spieler 3");
        int seconds = 5;
        Instant after5Seconds = start.plusSeconds(seconds);
        startedSpielHelper.addToHeim(spieler3, after5Seconds.toEpochMilli());
        startedSpielHelper.removeFromHeim(spieler2, after5Seconds.toEpochMilli());

        Instant after10Seconds = after5Seconds.plusSeconds(seconds);
        startedSpielHelper.removeFromHeim(spieler3, after10Seconds.toEpochMilli());
        startedSpielHelper.addToHeim(spieler2, after10Seconds.toEpochMilli());

        Team heimTeamAtStart = startedSpielHelper.findCurrentHeimTeam(start.toEpochMilli());
        assertTrue(heimTeamAtStart.isMember(spieler2));
        assertFalse(heimTeamAtStart.isMember(spieler3));

        Team heimTeamAfter5Seconds = startedSpielHelper.findCurrentHeimTeam(after5Seconds.toEpochMilli());
        assertFalse(heimTeamAfter5Seconds.isMember(spieler2));
        assertTrue(heimTeamAfter5Seconds.isMember(spieler3));

        Team heimTeamAfter10Seconds = startedSpielHelper.findCurrentHeimTeam(after10Seconds.toEpochMilli());
        assertTrue(heimTeamAfter10Seconds.isMember(spieler2));
        assertFalse(heimTeamAfter10Seconds.isMember(spieler3));

    }

    @Test
    void findCurrentAuswaertsTeam() {
        Spieler spieler2 = new Spieler("Spieler 2");
        startedSpielHelper.addToAuswaerts(spieler2, start.toEpochMilli());

        Spieler spieler3 = new Spieler("Spieler 3");
        int seconds = 5;
        Instant after5Seconds = start.plusSeconds(seconds);
        startedSpielHelper.addToAuswaerts(spieler3, after5Seconds.toEpochMilli());

        Team auswaertsAt3Sec = startedSpielHelper.findCurrentAuswaertsTeam(start.plusSeconds(3).toEpochMilli());
        assertTrue(auswaertsAt3Sec.isMember(spieler2));
        assertFalse(auswaertsAt3Sec.isMember(spieler3));

        Team auswaertsAt6Sec = startedSpielHelper.findCurrentAuswaertsTeam(start.plusSeconds(6).toEpochMilli());
        assertTrue(auswaertsAt6Sec.isMember(spieler2));
        assertTrue(auswaertsAt6Sec.isMember(spieler3));
    }
}