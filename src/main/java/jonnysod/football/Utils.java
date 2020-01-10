package jonnysod.football;

import java.util.Date;
import java.util.HashMap;

public class Utils {
    public static Date dateFormExport(Object dateExport) {
        if (dateExport instanceof Long) {
            return new Date((Long)dateExport);
        } else if (dateExport == null) {
            return null;
        } else {
            Long startL = (Long) ((HashMap<String, Object>) dateExport).get("time");
            return new Date(startL);
        }
    }
}
