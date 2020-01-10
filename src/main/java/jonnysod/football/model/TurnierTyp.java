package jonnysod.football.model;

public enum TurnierTyp {
	EWIGELIGA(1),MEISTERSCHAFT(2);
	
	private int id;

	private TurnierTyp(int id) {
		this.id = id;
	}

	public static TurnierTyp getTyp(long id) {
		return getTyp((long)id);
	}

	public static TurnierTyp getTyp(int id) {
		for (TurnierTyp typ : values()) {
			if(typ.id == id) {
				return typ;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}
}
