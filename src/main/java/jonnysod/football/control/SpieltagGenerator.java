package jonnysod.football.control;


import java.util.List;

import jonnysod.football.model.Spieltag;
import jonnysod.football.model.Team;

public interface SpieltagGenerator {

	Spieltag generateSpieltag(List<Team> teams,
			TurnierOptionen optionen);

}