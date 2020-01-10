package jonnysod.football.control;


import jonnysod.football.model.Team;

public class TeamSpieleHistorie implements Comparable<TeamSpieleHistorie> {
	Team team;
	int spiele;
	int spieleInFolge;
	int spieleGewonnen;
	int spieleHeim;

	public TeamSpieleHistorie(Team team) {
		super();
		this.team = team;
	}

	@Override
	public int compareTo(TeamSpieleHistorie another) {
		if (this.spiele < another.spiele) {
			return -1;
		} else if (this.spiele > another.spiele) {
			return 1;
		} else if (this.spieleInFolge < another.spieleInFolge) {
			return -1;
		} else if (this.spieleInFolge > another.spieleInFolge) {
			return 1;
		} else if (this.spieleGewonnen > another.spieleGewonnen) {
			return -1;
		} else if (this.spieleGewonnen < another.spieleGewonnen) {
			return 1;
		} else if (this.spieleHeim < another.spieleHeim) {
			return -1;
		} else if (this.spieleHeim > another.spieleHeim) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return team.toString() + " spiele=" + spiele + " spieleInFolge="
				+ spieleInFolge + " spieleGewonnen=" + spieleGewonnen
				+ " spieleHeim=" + spieleHeim;
	}
}
