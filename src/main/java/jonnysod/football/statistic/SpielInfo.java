package jonnysod.football.statistic;

import jonnysod.football.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonny on 04.07.15.
 */
public class SpielInfo {

    private Spiel s;

    public SpielInfo(Spiel s) {
        this.s = s;
    }

    public boolean teilnehmer(Team team) {
        return team.getId().equals(s.getHeim().getId())
                || team.getId().equals(s.getAuswaerts().getId());
    }

    public Ereignis findEreignis(long ereignisId) {
        Ereignis ret = null;
        List<Ereignis> allEreignisList = this.getEreignisseUndFolgeEreignisse();
        for (int i = 0; i < allEreignisList.size(); i++) {
            Ereignis e = allEreignisList.get(i);
            if (e.getId().equals(ereignisId)) {
                ret = e;
            }
        }
        return ret;
    }

    public List<Ereignis> getEreignisseUndFolgeEreignisse() {
        List<Ereignis> alleEs = new ArrayList<Ereignis>();
        for (Ereignis spielEreignis : this.s.getEreignisList()) {
            alleEs.add(spielEreignis);
            for (Ereignis folgeEreignis : spielEreignis.getFolgeEreignisse()) {
                alleEs.add(folgeEreignis);
            }
        }
        return alleEs;
    }

    public void resetData() {
        s.setStart(null);
        s.setPausedauerInMillisec(0L);
        s.setLastPause(null);
    }

    public boolean isBeendet() {
        return s.getEnd() != null;
    }

    public List<Spieler> findAlleSpieler() {
        SpielTeam heim = s.getHeim();
        SpielTeam auswaerts = s.getAuswaerts();
        List<Spieler> alleSpieler = new ArrayList<Spieler>(
                heim.getSpieler().size() + auswaerts.getSpieler().size());
        alleSpieler.addAll(heim.getSpieler());
        alleSpieler.addAll(auswaerts.getSpieler());
        return alleSpieler;
    }

    public Spieler findSpieler(SpielTeam t, Spieler spieler) {
        return findSpielSpieler(t, spieler).getSpieler();
    }

    public SpielSpieler findSpielSpieler(SpielTeam t, Spieler s) {
        for (SpielSpieler spielSpieler : t.getSpielerList()) {
            if (spielSpieler.getSpieler().equals(s)) {
                return spielSpieler;
            }
        }
        return null;
    }

    public Team findTeam(Team team) {
        if (s.getHeim().equals(team)) {
            return s.getHeimTeam();
        } else if (s.getAuswaertsTeam().equals(team)) {
            return s.getAuswaertsTeam();
        } else {
            return null;
        }
    }

    /**
     * Findet den Gegner.
     *
     * @param team
     * @return das gegnerische {@link Team} oder <code>null</code> wenn team
     *         nicht am Spiel teilnimmt;
     */
    public Team findGegner(Team team) {
        if (team.equals(s.getHeimTeam())) {
            return s.getAuswaertsTeam();
        } else if (team.equals(s.getAuswaertsTeam())) {
            return s.getHeimTeam();
        } else {
            return null;
        }
    }

    public int calcTore(SpielTeam spielTeam) {
        return calcTore(spielTeam.getTeam());
    }

    public int calcTore(Team team) {
        int tore = 0;
        for (Ereignis e : s.getEreignisList()) {
           if (e.getTeam() != null
                   && e.getTeam().equals(team)
                   && (EreignisTyp.TOR == e.getTyp()
                            || EreignisTyp.EIGENTOR == e.getTyp())) {
                tore += 1;
            }
        }
        return tore;
    }

    public int calcGegentore(Team team) {
        Team gegner = findGegner(team);
        return calcTore(gegner);
    }

    public boolean isStarted() {
        return s.getStart() != null;
    }

    public boolean sieg(Team team) {
        return calcTore(findTeam(team)) > calcTore(findGegner(team));
    }

    public boolean niederlage(Team team) {
        Team gegner = findGegner(team);
        return sieg(gegner);
    }

    public boolean unentschieden() {
        return calcTore(s.getAuswaertsTeam()) == calcTore(s.getHeimTeam());
    }

    public int punkte(Team team) {
        if (sieg(team)) {
            return 3;
        } else if (unentschieden()) {
            return 1;
        } else {
            return 0;
        }
    }

    public long calcSpieldauer(Long now) {
        if (s.getStart()==null) {
            return 0;
        }
        Long referenceTime = now;
        if (!s.isRunning()) {
            referenceTime = s.getLastPause();
        }
        return (referenceTime - s.getStart()) - s.getPausedauerInMillisec();
    }

    public int zeitpunktInSekunden(Long now) {
        return Math.toIntExact((calcSpieldauer(now) / 1000));
    }

    public boolean isEnded() {
        return s.getEnd() != null;
    }
}
