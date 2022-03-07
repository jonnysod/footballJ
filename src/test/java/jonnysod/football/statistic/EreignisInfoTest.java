package jonnysod.football.statistic;

import jonnysod.football.control.AlleGegenAlleGenerator;
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

    private Spieler spieler1Team1;
    private Spieler spieler2Team1;
    private Spieler spieler1Team2;
    private Spieler spieler2Team2;
    private Instant start;
    private Spiel spiel;

    @BeforeEach
    void setUp() {
        Team team1 = new Team("Team1", "Team 1");
        Team team2 = new Team("Team2", "Team 2");
        spieler1Team1 = new Spieler("Spieler 1 Team 1");
        spieler2Team1 = new Spieler("Spieler 2 Team 1");
        team1.getSpieler().add(spieler1Team1);
        team1.getSpieler().add(spieler2Team1);
        spieler1Team2 = new Spieler("Spieler 1 Team 2");
        spieler2Team2 = new Spieler("Spieler 2 Team 2");
        team2.getSpieler().add(spieler1Team2);
        team2.getSpieler().add(spieler2Team2);
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
        tor.setTeam(spiel.getHeim().getTeam());
        spiel.getEreignisList().add(tor);
        EreignisInfo infoTor = new EreignisInfo(tor);
        Team vorlagengeber = infoTor.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(2, vorlagengeber.getSpieler().size());
        tor.setSpieler(vorlagengeber.getSpieler().get(0));
        Team vorlagengeber2 = infoTor.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(Arrays.asList(vorlagengeber.getSpieler().get(1)), vorlagengeber2.getSpieler());

        Ereignis eigentor = new Ereignis(EreignisTyp.EIGENTOR, 5, after5Seconds.toEpochMilli());
        spiel.getEreignisList().add(eigentor);
        EreignisInfo infoEigentor = new EreignisInfo(eigentor);
        eigentor.setTeam(spiel.getHeim().getTeam());
        Team vorlagengeberEigentor = infoEigentor.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(2, vorlagengeberEigentor.getSpieler().size());
        eigentor.setSpieler(spiel.getAuswaerts().getSpieler().get(0));
        assertEquals(2, vorlagengeberEigentor.getSpieler().size());
    }

    @Test
    void findMoeglicheSchuetzen() {
        Instant after5Seconds = start.plusSeconds(5);
        Ereignis tor = new Ereignis(EreignisTyp.TOR, 5, after5Seconds.toEpochMilli());
        spiel.getEreignisList().add(tor);
        EreignisInfo info = new EreignisInfo(tor);
        Team schuetzen = info.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(2, schuetzen.getSpieler().size());
        tor.setSpieler(schuetzen.getSpieler().get(0));
        Team schuetzen2 = info.findMoeglicheVorlagengeber(spiel.getHeim());
        assertEquals(1, schuetzen2.getSpieler().size());
    }
}