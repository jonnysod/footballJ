package jonnysod.football.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Wettbewerb implements Serializable {

	private String id;
	private String name;
	private Long timestamp;
	private ArrayList<Turnier> turniers = new ArrayList<>();
	private ArrayList<Spieler> spielers = new ArrayList<>();

	public Wettbewerb() {
	}

	public Wettbewerb(String name, Long timestamp) {
		this.name = name;
		this.timestamp = timestamp;
	}

	public Map<String, Spieler> createSpielerMap() {
		Map<String, Spieler> spielerMap = new HashMap<>();
		for (Spieler s : spielers) {
			spielerMap.put(s.getId(), s);
		}
		return spielerMap;
	}

	public Spieler findSpieler(String spielerId) {
        return createSpielerMap().get(spielerId);
	}

    @Override
    public String toString() {
        return name;
    }

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Spieler> getSpielers() {
		return spielers;
	}

	public ArrayList<Turnier> getTurniers() {
		return turniers;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
