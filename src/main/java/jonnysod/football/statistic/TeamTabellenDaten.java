package jonnysod.football.statistic;


import jonnysod.football.model.Team;

public class TeamTabellenDaten implements Comparable<TeamTabellenDaten>{

	public Team team;
	public int spiele = 0;
	public int gewonnen = 0;
	public int unentschieden = 0;
	public int verloren = 0;
	public int punkte = 0;
	public int torePlus = 0;
	public int toreMinus = 0;

	public TeamTabellenDaten() { }

	public TeamTabellenDaten(Team team) {
		super();
		this.team = team;
	}

	@Override
	public int compareTo(TeamTabellenDaten anderesTeam) {
		if (punkte<anderesTeam.punkte) {
			return 1;
		} else if (punkte>anderesTeam.punkte) {
			return -1;
		} else { //punkte == anderesTeam.punkte
			return bessereTordifferenz(anderesTeam);
		}
	}

	private int bessereTordifferenz(TeamTabellenDaten anderesTeam) {
		if (torDifferenz()<anderesTeam.torDifferenz()) {
			return 1;
		} else if (torDifferenz()>anderesTeam.torDifferenz()) {
			return -1;
		} else { //tordifferenz==anderesTeam.tordifferenz
			return mehrToreGeschossen(anderesTeam);
		}
	}

	private int mehrToreGeschossen(TeamTabellenDaten anderesTeam) {
		if (torePlus<anderesTeam.torePlus) {
			return 1;
		} else if (torePlus>anderesTeam.torePlus) {
			return -1;
		} else { //torePlus == anderesTeam.torePlus
			return 0;// TODO direkter Vergleich?
		}
	}

	public int torDifferenz() {
		return torePlus-toreMinus;
	}
	
	@Override
	public String toString() {
		String tabellenZeile = "%s\t\t\t%3d\t\t%2d\t%2d\t%2d\t\t%3d:%-3d\t\t%3d\t\t%3d";
		return String.format(tabellenZeile,
				team.getName(), spiele, gewonnen, unentschieden, verloren, torePlus, toreMinus, (torePlus-toreMinus), punkte);
	}

    public Team getTeam() {
        return team;
    }

    public int getSpiele() {
        return spiele;
    }

    public int getGewonnen() {
        return gewonnen;
    }

    public int getUnentschieden() {
        return unentschieden;
    }

    public int getVerloren() {
        return verloren;
    }

    public int getPunkte() {
        return punkte;
    }

    public int getTorePlus() {
        return torePlus;
    }

    public int getToreMinus() {
        return toreMinus;
    }
}
