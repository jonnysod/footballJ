package jonnysod.football.statistic;

import java.util.Comparator;

public class ScorerComparator implements Comparator<SpielerStatistik> {

	@Override
	public int compare(SpielerStatistik lhs, SpielerStatistik rhs) {
		if (lhs.getScorerpoints() < rhs.getScorerpoints()) {
			return 1;
		} else if (lhs.getScorerpoints() > rhs.getScorerpoints()) {
			return -1;
		} else {
			if (lhs.getTore() < rhs.getTore()) {
				return 1;
			}
			return 0;
		}
	}

}
