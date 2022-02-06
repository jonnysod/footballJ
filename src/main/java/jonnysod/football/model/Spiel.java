package jonnysod.football.model;

import java.io.Serializable;
import java.util.*;

public class Spiel implements Serializable {

	private List<Ereignis> ereignisList = new ArrayList<>();
	private String id;
	private SpielTeam heim;
	private SpielTeam auswaerts;
	private int spiellaengeInSekunden;
	private Long pausedauerInMillisec = 0L;
	private Long start = null;
	private Long ende = null;
    private Long lastPause = null;

	public Spiel() {
	}

	public Spiel(Team heim, Team auswaerts, int spiellaengeInSekunden) {
		this.heim = new SpielTeam(heim);
		this.auswaerts = new SpielTeam(auswaerts);
		this.spiellaengeInSekunden = spiellaengeInSekunden;
	}

	public Spiel(String id, SpielTeam heim, SpielTeam auswaerts, int spiellaengeInSekunden) {
		this.id = id;
		this.heim = heim;
		this.auswaerts = auswaerts;
		this.spiellaengeInSekunden = spiellaengeInSekunden;
	}

	public Team findSpielTeam(String id) {
		if (heim.getTeam().getId().equals(id)) {
			return heim.getTeam();
		} else {
			return auswaerts.getTeam();
		}
	}

	public List<Ereignis> getEreignisList() {
		return ereignisList;
	}

	public void pause(Long now) {
		lastPause = now;
	}

	public void ende(Long now) {
	    if (isRunning()) {
			pause(now);
		}
		ende = lastPause;
	}

	public void resume(Long now) {
		if (start == null) {
			start = now;
		}
		ende = null;
		long oldPauseDur = pausedauerInMillisec;
		long lastPauseDur = 0;
		if (lastPause != null) {
			lastPauseDur = now - lastPause;
		}
		pausedauerInMillisec = oldPauseDur + lastPauseDur;
		lastPause = null;
	}

	public boolean isRunning() {
		return start != null && lastPause == null;
	}

	public boolean isStarted() {
		return start != null;
	}

	public boolean isEnded() {
		return ende != null;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Spiel) {
			Spiel other = (Spiel) o;
			if (id != null) {
				return id.equals(other.getId());
			} else {
				return other.getHeim().equals(getHeim()) && other.getAuswaerts().equals(getAuswaerts());
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		} else {
			return super.hashCode();
		}
	}

	public Spiel clone1() {
		Spiel spiel = new Spiel(heim.getTeam(), auswaerts.getTeam(), spiellaengeInSekunden);
		spiel.start = start;
		spiel.pausedauerInMillisec = pausedauerInMillisec;
		spiel.ende = ende;
		spiel.lastPause = lastPause;
		spiel.getEreignisList().addAll(this.ereignisList);
		return spiel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SpielTeam getHeim() {
		return heim;
	}

	public SpielTeam getAuswaerts() {
		return auswaerts;
	}

	public Team getHeimTeam() {
		return heim.getTeam();
	}

	public Team getAuswaertsTeam() {
		return auswaerts.getTeam();
	}

	public int getSpiellaengeInSekunden() {
		return spiellaengeInSekunden;
	}

	public Long getPausedauerInMillisec() {
		return pausedauerInMillisec;
	}

	public void setPausedauerInMillisec(Long pausedauerInMillisec) {
		this.pausedauerInMillisec = pausedauerInMillisec;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return ende;
	}

	public void setEnd(Long ende) {
		this.ende = ende;
	}

	public Long getLastPause() {
		return lastPause;
	}

	public void setLastPause(Long lastPause) {
		this.lastPause = lastPause;
	}

}