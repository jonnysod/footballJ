package jonnysod.football.statistic;

import java.util.Comparator;

public class ToreComparator implements Comparator<SpielerStatistik> {

	@Override
	public int compare(SpielerStatistik lhs, SpielerStatistik rhs) {
		if (lhs.getTore() < rhs.getTore()) {
			return -1;
		} else if (lhs.getTore() > rhs.getTore()) {
			return 1;
		} else {
			return 0;
		}
	}

}
