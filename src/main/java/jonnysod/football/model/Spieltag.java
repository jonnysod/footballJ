package jonnysod.football.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

public class Spieltag implements Serializable, Iterable<Spiel> {

    private List<Spiel> spiels = new ArrayList<>();
	private String id;
	private String name;
	private Date start;

	public Spieltag() {
	}

	public Spieltag(Date start) {
		super();
		this.start = start;
		this.name = getFormattedErstellungsDatum();
	}

	public boolean addAll(Collection<? extends Spiel> collection) {
		return spiels.addAll(collection);
	}

	public int size() {
		return spiels.size();
	}

	public Spiel get(int i) {
		return spiels.get(i);
	}

	public boolean contains(Object o) {
		return spiels.contains(o);
	}

	public boolean add(Spiel spiel) {
		return spiels.add(spiel);
	}

	public boolean remove(Object o) {
		return spiels.remove(o);
	}

	public void add(int i, Spiel spiel) {
		spiels.add(i, spiel);
	}

	public Spiel remove(int i) {
		return spiels.remove(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	private String getFormattedErstellungsDatum() {
		if (start != null) {
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			return df.format(start);
		} else {
			return "Kein Datum";
		}
	}

	public Spiel findSpiel(String spielId) {
        for (Spiel s : this.spiels) {
            if (spielId.equals(s.getId())) return s;
        }
        return null;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Spiel> getSpiels() {
		return spiels;
	}

	@Override
	public Iterator<Spiel> iterator() {
		return spiels.iterator();
	}
}