package jonnysod.football.control;

import jonnysod.football.model.Spiel;
import jonnysod.football.model.Spieler;
import jonnysod.football.model.Spieltag;
import jonnysod.football.model.Team;
import jonnysod.football.statistic.SpielInfo;
import net.jqwik.api.*;
import net.jqwik.api.constraints.Size;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlleGegenAlleGeneratorTest {

    @Property
    public void testGenerateSpieltag(@ForAll @Size(min = 2, max = 10)List<@From("genTeam") Team> teamList) {
        SpieltagGenerator generator = new AlleGegenAlleGenerator();
        TurnierOptionen optionen = new TurnierOptionen(60);
        Spieltag spieltag = generator.generateSpieltag(teamList, optionen);
        assertTrue(spieltag.size() >= teamList.size());
    }

    @Property
    public void testAllSameNumberOfSpiele(@ForAll @Size(min = 2, max = 10)List<@From("genTeam") Team> teamList) {
        SpieltagGenerator generator = new AlleGegenAlleGenerator();
        TurnierOptionen optionen = new TurnierOptionen(60);
        Spieltag spieltag = generator.generateSpieltag(teamList, optionen);

        Map<Team, Integer> numberOfSpieleMap = new HashMap<>();
        for (Team team: teamList) {
            int numberOfSpiele = 0;
            if (numberOfSpieleMap.get(team) != null) {
                numberOfSpiele = numberOfSpieleMap.get(team);
            }
            for (Spiel spiel: spieltag ) {
                if(new SpielInfo(spiel).teilnehmer(team)) {
                    numberOfSpiele++;
                }
            }
            numberOfSpieleMap.put(team, numberOfSpiele);
        }
        Integer numberOfSpiele = null;
        for(Integer numberOfSpielTeam: numberOfSpieleMap.values()) {
            if (numberOfSpiele == null) {
                numberOfSpiele = numberOfSpielTeam;
            } else {
                assertSame(numberOfSpiele, numberOfSpielTeam);
            }
        }
    }

    @Provide
    Arbitrary<Team> genTeam() {
        Arbitrary<String> nameGen = Arbitraries.strings().withCharRange('a', 'z')
                .ofMinLength(3).ofMaxLength(10).unique();
        Arbitrary<Spieler> spielerGen = genSpieler();
        Arbitrary<List<Spieler>> spielerListGen = spielerGen.collect(list -> list.size() > 5);
        return Combinators.combine(nameGen, spielerListGen).as((name, spielerList) -> {
            Team team = new Team(name, name);
            team.getSpieler().addAll(spielerList);
            return team;
        });
    }

    @Provide
    Arbitrary<Spieler> genSpieler() {
        Arbitrary<String> nameGen = Arbitraries.strings().withCharRange('a', 'z')
                .ofMinLength(3).ofMaxLength(10);
        return Combinators.combine(nameGen, nameGen).as((name, id) -> {
            Spieler spieler = new Spieler();
            spieler.setName(name);
            spieler.setId(id);
            return spieler;
        });
    }
}
