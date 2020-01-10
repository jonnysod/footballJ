package jonnysod.football.model;

/**
 * Created by jonathan on 13/03/16.
 */
public class TeamInfo {
    public boolean teamHasSpiele = false;
    public boolean teamHasGestarteteSpiele = false;

    public TeamInfo(boolean teamHasSpiele, boolean teamHasGestarteteSpiele) {
        this.teamHasSpiele = teamHasSpiele;
        this.teamHasGestarteteSpiele = teamHasGestarteteSpiele;
    }
}
