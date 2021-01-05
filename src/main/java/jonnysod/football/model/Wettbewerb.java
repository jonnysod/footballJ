package jonnysod.football.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Wettbewerb implements Serializable {

    private ArrayList<Turnier> turniers = new ArrayList<>();
	private String id;
	private String name;
	private final ArrayList<Spieler> spieler = new ArrayList<Spieler>();

	public Wettbewerb() {
	}

	public Wettbewerb(String name) {
		this.name = name;
	}

	public Wettbewerb(String id, Map<String, Object> export) {
		this.id = id;
		this.name = (String) export.get("name");
	}

	public Map<String, Spieler> createSpielerMap() {
		Map<String, Spieler> spielerMap = new HashMap<>();
		for (Spieler s : spieler) {
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

	public ArrayList<Spieler> getSpieler() {
		return spieler;
	}

	public void setId(String id) {
		this.id = id;
	}
}
