package jonnysod.football.model;

import jonnysod.football.Utils;

import java.util.*;

public class Spiel extends ArrayList<Ereignis> {

	private String id;
	private Team heim;
	private Team auswaerts;
	private int spiellaengeInSekunden;
	private long pausedauerInMillisec = 0;
	private Date start = null;
	private Date ende = null;
    private Date lastPause = null;

	public Spiel() {
	}

	public Spiel(Team heim, Team auswaerts, int spiellaengeInSekunden) {
		this.heim = heim;
		this.auswaerts = auswaerts;
		this.spiellaengeInSekunden = spiellaengeInSekunden;
	}

	public Spiel(String id, Map<String, Object> export) {
		this.id = id;
		importBaseMap(export);
	}

	public Team findSpielTeam(String id) {
		if (heim.getId().equals(id)) {
			return heim;
		} else {
			return auswaerts;
		}
	}

	public void importBaseMap(Map<String, Object> export) {
		this.spiellaengeInSekunden = ((Number) export.get("spiellaengeInSekunden")).intValue();
		this.pausedauerInMillisec = ((Number) export.get("pausedauerInMillisec")).longValue();
		this.start = Utils.dateFormExport(export.get("start"));
		this.ende = Utils.dateFormExport(export.get("ende"));
		this.lastPause = Utils.dateFormExport(export.get("lastPause"));
	}

	public void pause(Date now) {
		lastPause = now;
	}

	public void ende(Date now) {
	    if (isRunning()) {
			pause(now);
		}
		ende = lastPause;
	}

	public void resume(Date now) {
		if (start == null) {
			start = now;
		}
		ende = null;
		long oldPauseDur = pausedauerInMillisec;
		long lastPauseDur = 0;
		if (lastPause != null) {
			lastPauseDur = now.getTime() - lastPause.getTime();
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
				return other.getHeim().equals(heim) && other.getAuswaerts().equals(auswaerts);
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

	public Map<String, Object> exportBaseMap() {
		Map export = new HashMap<String,String>();
		export.put("spiellaengeInSekunden", spiellaengeInSekunden);
		export.put("pausedauerInMillisec", pausedauerInMillisec);
		if (start != null) export.put("start", start.getTime());
		if (ende != null) export.put("ende", ende.getTime());
		if (lastPause != null) export.put("lastPause", lastPause.getTime());
		return export;
	}

	public Spiel clone() {
		Spiel spiel = new Spiel(heim, auswaerts, spiellaengeInSekunden);
		spiel.start = start;
		spiel.pausedauerInMillisec = pausedauerInMillisec;
		spiel.ende = ende;
		spiel.lastPause = lastPause;
		spiel.addAll(this);
		return spiel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Team getHeim() {
		return heim;
	}

	public void setHeim(Team heim) {
		this.heim = heim;
	}

	public Team getAuswaerts() {
		return auswaerts;
	}

	public void setAuswaerts(Team auswaerts) {
		this.auswaerts = auswaerts;
	}

	public int getSpiellaengeInSekunden() {
		return spiellaengeInSekunden;
	}

	public long getPausedauerInMillisec() {
		return pausedauerInMillisec;
	}

	public void setPausedauerInMillisec(long pausedauerInMillisec) {
		this.pausedauerInMillisec = pausedauerInMillisec;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return ende;
	}

	public void setEnd(Date ende) {
		this.ende = ende;
	}

	public Date getLastPause() {
		return lastPause;
	}

	public void setLastPause(Date lastPause) {
		this.lastPause = lastPause;
	}
}