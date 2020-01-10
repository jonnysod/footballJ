package jonnysod.football.model;

public enum EreignisTyp {
	TOR(0), VORLAGE(1), EIGENTOR(2),
	ANPFIFF(3), ABPFIFF(4), HALBZEITPFIFF(5),
	FOUL(6), STRAFSTOSS(7), FREISTOSS(8),
	EINWURF(9), ECKE(10);
	
	public int id;

	private EreignisTyp(int id) {
		this.id = id;
	}
	
	public static EreignisTyp getTyp(int id) {
		for (EreignisTyp typ : values()) {
			if(typ.id == id) {
				return typ;
			}
		}
		return null;
	}
}
