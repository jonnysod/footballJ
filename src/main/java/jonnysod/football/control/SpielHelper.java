package jonnysod.football.control;

import jonnysod.football.model.*;
import jonnysod.football.statistic.SpielInfo;

import java.util.List;

public class SpielHelper {

    Spiel spiel;

    public SpielHelper(Spiel spiel) {
        this.spiel = spiel;
    }

    /**
     * Add a `spieler` to `SpielTeam` from heim `Team`. Spieler should only be added, if it is currently not in the
     * `SpielTeam`. It is added to `zeitpunktInSekunden` given by `ts`.
     *
     * @param spieler
     * @param ts
     * @return Boolean indicating if the spieler was successfully added
     */
    public boolean addToHeim(Spieler spieler, Long ts) {
        return addTo(spiel.getHeim(), spieler, ts);
    }

    public boolean addToAuswaerts(Spieler spieler, Long ts) {
        return addTo(spiel.getAuswaerts(), spieler, ts);
    }

    private boolean addTo(SpielTeam team, Spieler spieler, Long ts) {
        SpielInfo info = new SpielInfo(spiel);
        if (!info.isBeendet()) {
            SpielSpieler spielSpieler = info.findSpielSpieler(team, spieler);
            int zeitpunktInSekunden = info.zeitpunktInSekunden(ts);
            if (spielSpieler == null) {
                spielSpieler = new SpielSpieler(spieler, zeitpunktInSekunden);
                team.getSpielerList().add(spielSpieler);
            }
            List<Integer> eintrittList = spielSpieler.getEintrittsZeitpunktInSekunden();
            boolean zeitpunktAfterLastEintritt = eintrittList.isEmpty()
                    || zeitpunktInSekunden > eintrittList.get(eintrittList.size() - 1);
            if (!isInTeam(spielSpieler, zeitpunktInSekunden) && zeitpunktAfterLastEintritt) {
                List<Integer> austrittList = spielSpieler.getAustrittsZeitpunktInSekunden();
                int lastAustrittIndex = austrittList.size() - 1;
                if (!austrittList.isEmpty() && zeitpunktInSekunden <= austrittList.get(lastAustrittIndex)) {
                    austrittList.remove(lastAustrittIndex);
                } else {
                    // By the way, this is the normal case but because of java couldn't put it in front
                    eintrittList.add(zeitpunktInSekunden);
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean removeFromHeim(Spieler spieler, Long ts) {
        return removeFrom(spiel.getHeim(), spieler, ts);
    }

    public boolean removeFromAuswaerts(Spieler spieler, Long ts) {
        return removeFrom(spiel.getAuswaerts(), spieler, ts);
    }

    private boolean removeFrom(SpielTeam team, Spieler spieler, Long ts) {
        SpielInfo info = new SpielInfo(spiel);
        SpielSpieler spielSpieler = info.findSpielSpieler(team, spieler);
        if (!info.isBeendet()) {
            if (info.isStarted()) {
                int zeitpunktInSekunden = info.zeitpunktInSekunden(ts);
                List<Integer> austrittList = spielSpieler.getAustrittsZeitpunktInSekunden();
                boolean zeitpunktAfterLastAustritt = austrittList.isEmpty()
                        || zeitpunktInSekunden > austrittList.get(austrittList.size() - 1);
                List<Integer> eintrittList = spielSpieler.getEintrittsZeitpunktInSekunden();
                int lastEintrittIndex = eintrittList.size() - 1;
                if (zeitpunktAfterLastAustritt) {
                    if (!eintrittList.isEmpty() && zeitpunktInSekunden <= eintrittList.get(lastEintrittIndex)) {
                        eintrittList.remove(lastEintrittIndex);
                        if (eintrittList.isEmpty()) {
                            team.getSpielerList().remove(spielSpieler);
                        }
                        return true;
                    } else if (isInTeam(spielSpieler, zeitpunktInSekunden)) {
                        // Again the normal case but I wasn't able to write it at top of if-else
                        return spielSpieler.getAustrittsZeitpunktInSekunden().add(zeitpunktInSekunden);
                    }
                }
            } else {
                return team.getSpielerList().remove(spielSpieler);
            }
        }
        return false;
    }

    public Team findCurrentHeimTeam(Long currentTs) {
        SpielInfo info = new SpielInfo(spiel);
        int zeitpunktInSekunden = info.zeitpunktInSekunden(currentTs);
        return findCurrentHeimTeam(zeitpunktInSekunden);
    }

    public Team findCurrentHeimTeam(Integer zeitpunktInSekunden) {
        return findCurrentTeam(spiel.getHeim(), zeitpunktInSekunden);
    }

    public Team findCurrentAuswaertsTeam(Long currentTs) {
        SpielInfo info = new SpielInfo(spiel);
        int zeitpunktInSekunden = info.zeitpunktInSekunden(currentTs);
        return findCurrentAuswaertsTeam(zeitpunktInSekunden);
    }

    public Team findCurrentAuswaertsTeam(Integer zeitpunktInSekunden) {
        return findCurrentTeam(spiel.getAuswaerts(), zeitpunktInSekunden);
    }

    public static Team findCurrentTeam(SpielTeam spielTeam, Integer zeitpunktInSekunden) {
        Team team = new Team(spielTeam.getId(), spielTeam.getName());
        for (SpielSpieler spielSpieler: spielTeam.getSpielerList()) {
            if (isInTeam(spielSpieler, zeitpunktInSekunden)) {
                team.getSpieler().add(spielSpieler.getSpieler());
            }
        }
        return team;
    }

    private static boolean isInTeam(SpielSpieler spieler, int zeitpunktInSekunden) {
        List<Integer> eintrittsZeitpunktList = spieler.getEintrittsZeitpunktInSekunden();
        List<Integer> austrittsZeitpunktList = spieler.getAustrittsZeitpunktInSekunden();
        // We expect the spieler to not be in the team
        boolean isInTeam = false;
        // unless we find eintritt after `now` without and austritt before `now`
        for (int i = 0; i < eintrittsZeitpunktList.size(); i++) {
            int eintritt = eintrittsZeitpunktList.get(i);
            if (i < austrittsZeitpunktList.size()) {
                int austritt = austrittsZeitpunktList.get(i);
                isInTeam = ((zeitpunktInSekunden >= eintritt) && (zeitpunktInSekunden < austritt));
            } else {
                isInTeam = (zeitpunktInSekunden >= eintritt);
            }
            // If we found a time slot for which the spieler is in team we are done.
            if (isInTeam) {
                break;
            }
        }
        return isInTeam;
    }
}
