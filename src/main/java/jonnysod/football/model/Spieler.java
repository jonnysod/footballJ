package jonnysod.football.model;

import java.util.ArrayList;
import java.util.List;

public class Spieler {

	private String id;
	private String name;
	public final List<Integer> eintrittsZeitpunktInSekunden = new ArrayList<>();
	public final List<Integer> austrittsZeitpunktInSekunden = new ArrayList<>();

	public Spieler() {
		super();
	}
	
	public Spieler(String name, jonnysod.football.model.Wettbewerb wettbewerb) {
		super();
		this.name = name;
		wettbewerb.getSpieler().add(this);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this.getId() == null) {
			return super.equals(o);
		}
		if (o instanceof Spieler) {
			Spieler otherSpieler = (Spieler) o;
			if (this.getId().equals(otherSpieler.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (this.getId() == null) {
			return super.hashCode();
		} else {
			return this.getId().hashCode();
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
}