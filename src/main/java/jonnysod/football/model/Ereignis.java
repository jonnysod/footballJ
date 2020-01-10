package jonnysod.football.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Ereignis implements Comparable {

	private String id;
	private EreignisTyp typ;
	private int zeitpunktInSekunden;
	private Date uhrzeit;
	private Spieler spieler;
	private Team team;
	private final List<Ereignis> folgeEreignisse = new LinkedList<>();

	public Ereignis() {
	}

	public Ereignis(EreignisTyp typ, int zeitpunktInSekunden, Date uhrzeit) {
		this.typ = typ;
		this.zeitpunktInSekunden = zeitpunktInSekunden;
		this.uhrzeit = uhrzeit;
	}

	public Ereignis(EreignisTyp typ, Ereignis ereignis) {
		this.typ = typ;
		this.id = ereignis.getId();
		this.zeitpunktInSekunden = ereignis.zeitpunktInSekunden;
		this.uhrzeit = ereignis.uhrzeit;
		this.spieler = ereignis.spieler;
		this.team = ereignis.team;
		this.folgeEreignisse.addAll(ereignis.folgeEreignisse);
	}

	public Ereignis addFolgeEreignis(EreignisTyp ereignisTyp) {
		Ereignis folgeEreignis = new Ereignis(ereignisTyp, zeitpunktInSekunden, uhrzeit);
		folgeEreignis.team = team;
		addFolgeEreignis(folgeEreignis);
		return folgeEreignis;
	}

	public void addFolgeEreignis(Ereignis folgeEreignis) {
		folgeEreignisse.add(folgeEreignis);
	}

	public Ereignis getFolgeEreignis(EreignisTyp ereignisTyp) {
		for (Ereignis folgeEreignis: folgeEreignisse) {
			if (folgeEreignis.typ == ereignisTyp) return folgeEreignis;
		}
		return null;
	}

	public Ereignis removeFolgeEreignis(EreignisTyp ereignisTyp) {
		Ereignis folgeEreignis;
		for (int i = 0; i < folgeEreignisse.size(); i++) {
			if (folgeEreignisse.get(i).typ == ereignisTyp) {
				return folgeEreignisse.remove(i);
			}
		}
		return null;
	}

	public boolean hasFolgeEreignis() {
		return !folgeEreignisse.isEmpty();
	}

	@Override
	public int compareTo(Object o) {
		Ereignis other = (Ereignis) o;
		return new Integer(zeitpunktInSekunden).compareTo(other.zeitpunktInSekunden);
	}

    @Override
    public int hashCode() {
        if (id!=null) return id.hashCode();
        else return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ereignis && ((Ereignis)obj).getId() != null) {
            return id.equals(((Ereignis)obj).getId());
        }
        else return super.equals(obj);

    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EreignisTyp getTyp() {
		return typ;
	}

	public void setTyp(EreignisTyp typ) {
		this.typ = typ;
	}

	public int getZeitpunktInSekunden() {
		return zeitpunktInSekunden;
	}

	public void setZeitpunktInSekunden(int zeitpunktInSekunden) {
		this.zeitpunktInSekunden = zeitpunktInSekunden;
	}

	public Date getUhrzeit() {
		return uhrzeit;
	}

	public void setUhrzeit(Date uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Ereignis> getFolgeEreignisse() {
		return folgeEreignisse;
	}
}
