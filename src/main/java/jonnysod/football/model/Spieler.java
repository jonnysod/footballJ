package jonnysod.football.model;

import java.io.Serializable;

public class Spieler implements Serializable {

	private String id;
	private String name;

	public Spieler() {
		super();
	}
	
	public Spieler(String name) {
		super();
		this.name = name;
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