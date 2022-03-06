package jonnysod.football.statistic;

import jonnysod.football.control.AlleGegenAlleGenerator;
import jonnysod.football.control.SpielHelper;
import jonnysod.football.control.SpieltagGenerator;
import jonnysod.football.control.TurnierOptionen;
import jonnysod.football.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EreignisInfoTest {

    private Spieler spielerTeam1;
    private Spieler spielerTeam2;
    private Instant start;
    private Spiel spiel;

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
        spiel = spieltag.get(0);
        start = Instant.now();
    }

    @Test
    void findMoeglicheVorlagengeber() {
        Instant after5Seconds = start.plusSeconds(5);
        Ereignis tor = new Ereignis(EreignisTyp.TOR, 5, after5Seconds.toEpochMilli());
        spiel.getEreignisList().add(tor);
        EreignisInfo info = new EreignisInfo(tor);
        Team vorlagengeber = info.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(1, vorlagengeber.getSpieler().size());
        tor.setSpieler(vorlagengeber.getSpieler().get(0));
        Team vorlagengeber2 = info.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(0, vorlagengeber2.getSpieler().size());
    }

    @Test
    void findMoeglicheSchuetzen() {
        Instant after5Seconds = start.plusSeconds(5);
        Ereignis tor = new Ereignis(EreignisTyp.TOR, 5, after5Seconds.toEpochMilli());
        spiel.getEreignisList().add(tor);
        EreignisInfo info = new EreignisInfo(tor);
        Team schuetzen = info.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(1, schuetzen.getSpieler().size());
        tor.setSpieler(schuetzen.getSpieler().get(0));
        Team schuetzen2 = info.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(0, schuetzen2.getSpieler().size());
    }
}