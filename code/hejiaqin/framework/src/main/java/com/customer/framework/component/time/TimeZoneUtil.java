package com.customer.framework.component.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZoneUtil {
	public static final TimeZone getLocalTimeZone() {
		return TimeZone.getDefault();
	}

	public static String timeZoneTransform(String timeStr, TimeZone startZone, TimeZone endZone) {
		try {
			if (timeStr == null || timeStr.trim().length() == 0) {
				return "";
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			format.setTimeZone(startZone);
			Date date = format.parse(timeStr);
			format.setTimeZone(endZone);

			String str = format.format(date);
			return str;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TimeZone getTimeZone(int timeZoneOffset) {
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {
			timeZoneOffset = 0;
		}
		TimeZone timeZone;
		String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
		if (ids.length == 0) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
		}
		return timeZone;
	}

	public static TimeZone getTimeZone(String tZone) {
		TimeZone timeZone = null;
		if (tZone == null || tZone.trim().length() == 0) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = TimeZone.getTimeZone(tZone);
		}
		return timeZone;
	}
}
