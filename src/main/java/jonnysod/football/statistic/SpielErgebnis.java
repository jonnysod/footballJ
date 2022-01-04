package jonnysod.football.statistic;

import jonnysod.football.model.Ereignis;
import jonnysod.football.model.EreignisTyp;
import jonnysod.football.model.Spiel;
import jonnysod.football.model.Team;

import java.util.Map;

/**
 * Can be used to display a result up to a certain `Ereignis` a zwischenergebnis.
 *
 * Eg. 4 tor `Ereignisse` leading to a total score of 2:2 (`spielstand()`).
 * With this list of ereignisse [Tor(heim), Tor(auswaerts), Tor(auswaerts), Tor(heim)],
 * we would have this zwischenergebnisse:
 * 1:0
 * 1:1
 * 1:2
 * 2:2
 *
 * Additionally this prints out nicely which player scored and which player played the final pass.
 */
public class SpielErgebnis {

	Spiel spiel;
	Map<TextId, String> textMap;

    public SpielErgebnis(Spiel spiel, Map<TextId, String> textMap) {
        this.spiel = spiel;
        this.textMap = textMap;
    }

    public String getTitle() {
        return spiel.getHeim().getName() + " - " + spiel.getAuswaerts().getName() + "  " + spielstand();
    }
	
	public String spielstand() {
        SpielInfo spielInfo = new SpielInfo(spiel);
		if (spielInfo.isStarted()) {
			return spielInfo.calcTore(spiel.getHeim())+":"+spielInfo.calcTore(spiel.getAuswaerts());
		} else {
			return "-:-";
		}
	}

	public String printToreHeim() {
		return printTore(spiel.getHeim().getTeam());
	}

	public String printToreAuswaerts() {
		return printTore(spiel.getAuswaerts().getTeam());
	}
	private String printTore(Team team) {
		StringBuilder toreList = new StringBuilder();
		for (Ereignis e : spiel.getEreignisList()) {
			if (e.getTeam() != null
                    && e.getTeam().equals(team)
                    && ( EreignisTyp.TOR == e.getTyp()
							|| EreignisTyp.EIGENTOR == e.getTyp())) {
				toreList.append(printZeitpunktInMinuten(e));
				if (e.getSpieler() != null) {
					toreList.append(" ").append(e.getSpieler().getName());
				}
				if (EreignisTyp.EIGENTOR == e.getTyp()) {
					toreList.append("(E.)");
				}
				toreList.append("\n");
			}
		}
		return toreList.toString();
	}

	public SpielErgebnis calcZwischenergebnis(Ereignis bisEreignis) {
        Spiel spielBis =
                new Spiel(spiel.getHeim().getTeam(), spiel.getAuswaerts().getTeam(), spiel.getSpiellaengeInSekunden());
        spielBis.setStart(spiel.getStart());
		for (Ereignis e : spiel.getEreignisList()) {
            spielBis.getEreignisList().add(e);
			if (e==bisEreignis) {
				break;
			}
		}
		return new SpielErgebnis(spielBis, textMap);
	}

    public String printEreignisImSpielverlauf(Ereignis e) {
        EreignisTyp typ = e.getTyp();
        StringBuilder ereignis = new StringBuilder();
        if (EreignisTyp.TOR == typ
                || EreignisTyp.EIGENTOR == typ) {
            ereignis.append(calcZwischenergebnis(e).spielstand())
                    .append("  ");
            if (e.getSpieler() != null) {
                ereignis.append(e.getSpieler().getName());
            }
            ereignis.append(" (")
                    .append(printZeitpunktInMinuten(e));
            if (EreignisTyp.EIGENTOR == typ) {
                ereignis.append(", ")
                        .append(textMap.get(TextId.EIGENTOR));
            }
            if (!e.getFolgeEreignisse().isEmpty()) {
                Ereignis vorlage = e.getFolgeEreignisse().get(0);
                if (EreignisTyp.VORLAGE == vorlage.getTyp()) {
                    ereignis.append(", ")
                            .append(vorlage.getSpieler().getName());
                }
            }
            ereignis.append(")");
        }
        return ereignis.toString();
    }

    public String printTor(Ereignis e) {
        EreignisTyp typ = e.getTyp();
        StringBuilder tor = new StringBuilder();
        if (e.getSpieler() != null) {
            if (EreignisTyp.TOR == typ) {
                tor.append(textMap.get(TextId.TOR));
            } else if (EreignisTyp.EIGENTOR == typ) {
                tor.append(textMap.get(TextId.EIGENTOR));
            }
            tor.append(" ")
                    .append(textMap.get(TextId.VON))
                    .append(" ")
                    .append(e.getSpieler().getName());
        } else {
            tor.append(textMap.get(TextId.KEIN_TORSCHUETZE));
        }
        return tor.toString();
    }

    public String printVorlage(Ereignis e) {
        StringBuilder vorlageString = new StringBuilder();
        if (!e.getFolgeEreignisse().isEmpty()) {
            Ereignis vorlage = e.getFolgeEreignisse().get(0);
            if (EreignisTyp.VORLAGE == vorlage.getTyp()) {
                vorlageString
                        .append(textMap.get(TextId.VORLAGE))
                        .append(" ")
                        .append(vorlage.getSpieler().getName());
            }
        } else {
            vorlageString.append(textMap.get(TextId.KEINE_VORLAGE));
        }
        return vorlageString.toString();
    }

    public String printZeitpunktInMinuten(Ereignis e) {
        int minuten = e.getZeitpunktInSekunden() / 60;
        int sekunden = e.getZeitpunktInSekunden() % 60;
        String zeit = minuten+":";
        if (sekunden < 10) {
            zeit += 0;
        }
        zeit += sekunden;
        return zeit;
    }

    public String getSpielverlauf() {
        StringBuilder spielverlauf = new StringBuilder();
        for (Ereignis e : spiel.getEreignisList()) {
            EreignisTyp typ = e.getTyp();
            if (EreignisTyp.TOR == typ
                    || EreignisTyp.EIGENTOR == typ) {
                spielverlauf.append(printEreignisImSpielverlauf(e))
                        .append("\n");
            }
        }
        return spielverlauf.toString();
    }

    public enum TextId {
        TOR, EIGENTOR, VON, KEIN_TORSCHUETZE, VORLAGE, KEINE_VORLAGE
    }
}
