package jonnysod.football.model;

import java.io.Serializable;
import java.util.*;

public class Spiel implements Serializable, Iterable<Ereignis> {

	private List<Ereignis> ereignisList = new ArrayList<>();
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

	public Team findSpielTeam(String id) {
		if (heim.getId().equals(id)) {
			return heim;
		} else {
			return auswaerts;
		}
	}

	public int size() {
		return ereignisList.size();
	}

	public boolean isEmpty() {
		return ereignisList.isEmpty();
	}

	public boolean contains(Object o) {
		return ereignisList.contains(o);
	}

	public boolean add(Ereignis ereignis) {
		return ereignisList.add(ereignis);
	}

	public boolean remove(Object o) {
		return ereignisList.remove(o);
	}

	public boolean addAll(Collection<? extends Ereignis> collection) {
		return ereignisList.addAll(collection);
	}

	public void add(int i, Ereignis ereignis) {
		ereignisList.add(i, ereignis);
	}

	public Ereignis remove(int i) {
		return ereignisList.remove(i);
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

	public Spiel clone() {
		Spiel spiel = new Spiel(heim, auswaerts, spiellaengeInSekunden);
		spiel.start = start;
		spiel.pausedauerInMillisec = pausedauerInMillisec;
		spiel.ende = ende;
		spiel.lastPause = lastPause;
		spiel.addAll(this.ereignisList);
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

	@Override
	public Iterator<Ereignis> iterator() {
		return ereignisList.iterator();
	}
}