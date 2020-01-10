package jonnysod.football.model;

import jonnysod.football.Utils;

import java.text.DateFormat;
import java.util.*;

public class Spieltag extends ArrayList<Spiel> {

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

	public Spieltag(String id, Map<String, Object> export) {
		this.id = id;
		this.name = (String) export.get("name");
		this.start = Utils.dateFormExport(export.get("start"));
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
        for (Spiel s : this) {
            if (spielId.equals(s.getId())) return s;
        }
        return null;
    }

    public Map<String, Object> exportBaseMap() {
		Map export = new HashMap<String,String>();
		export.put("name", name);
		export.put("start", start.getTime());
		return export;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}