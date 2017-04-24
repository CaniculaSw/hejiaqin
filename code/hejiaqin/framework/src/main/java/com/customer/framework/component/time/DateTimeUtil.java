package com.customer.framework.component.time;

import android.text.TextUtils;
import android.text.format.DateFormat;

import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***/
public class DateTimeUtil {
    private static final String TAG = "TimeUtil";
    private static final String DATE_SPLIT = "-";

    private static final float MS = 1000.0f;

    public static String getCurrentDateString() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getCurrentDateString(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public static String getDDMMYYHHMMString(String time) {
        try {
            return new SimpleDateFormat("dd/MM/yy HH:mm").format(new SimpleDateFormat(
                    "yyyyMMddHHmmss").parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStandardDateString(String time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat(
                    "yyyyMMddHHmmss").parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateString(Date date) {
        if (null == date) {
            return getCurrentDateString();
        }
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    public static String getDiffTime(long diffTime) {
        long hourMarker = 60 * 60;
        long minuteMarker = 60;
        long secondMarker = 1;
        long hour = diffTime / hourMarker;
        long minute = (diffTime - hour * hourMarker) / minuteMarker;
        long second = (diffTime - hour * hourMarker - minute * minuteMarker) / secondMarker;
        DecimalFormat decfmt = new DecimalFormat();
        if (hour == 0) {
            return decfmt.format(minute) + ":" + decfmt.format(second);
        }

        return decfmt.format(hour) + ":" + decfmt.format(minute) + ":" + decfmt.format(second);
    }

    public static String getDiffTime(long diffTime, String minName, String secName) {
        long minuteMarker = 60;
        long secondMarker = 1;
        long minute = diffTime / minuteMarker;
        long second = (diffTime - minute * minuteMarker) / secondMarker;
        String strMin = String.valueOf(minute);
        String strSec = String.valueOf(second);
        if (minute > 0) {
            strMin = strMin + minName;
        } else {
            strMin = "";
        }
        strSec = strSec + secName;

        return strMin + strSec;
    }

    public static String getComparedTime(String yesterday, String time) {
        if (time == null || time.trim().length() == 0) {
            return null;
        }
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Date nowDate = new Date();
        String formatTime = null;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String lastTimeDate = df.format(date);

        if (df.format(nowDate).equals(lastTimeDate)) {
            df = new SimpleDateFormat("HH:mm", Locale.getDefault());
            formatTime = df.format(date);
        } else if (date.getTime() >= getYesterdayStart() && date.getTime() <= getYesterdayEnd()) {
            formatTime = yesterday;
        } else {
            formatTime = lastTimeDate;
        }
        return formatTime;
    }

    public static String getComparedTimeByDate(String yesterday, Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        Calendar c = Calendar.getInstance();
        Date nowDate = c.getTime();
        String formatTime = null;

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String lastTimeDate = df.format(date);

        if (df.format(nowDate).equals(lastTimeDate)) {
            df = new SimpleDateFormat("HH:mm", Locale.getDefault());
            formatTime = df.format(date);
        } else {
            c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
            if (df.format(c.getTime()).equals(lastTimeDate)) {
                formatTime = yesterday;
            } else {
                formatTime = lastTimeDate;
            }
        }
        return formatTime;
    }

    public static Date getYesterdayDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        return c.getTime();
    }

    public static String getComparedTimeByTime(String time, String yesterday) {
        Date lastDate = getYYYYMMDDTHHMMSStoLocalTime(time);
        if (lastDate == null) {
            return null;
        }
        return getComparedTimeByDate(yesterday, lastDate);
    }

    public static boolean isYesterday(Date date) {
        return null != date && date.getTime() >= getYesterdayStart()
                && date.getTime() <= getYesterdayEnd();
    }

    public static boolean isInNineDay(Date date) {
        return null != date && date.getTime() >= getNinedayStart()
                && date.getTime() <= getNinedayEnd();
    }

    public static boolean isThisYear(Date date) {
        return null != date && date.getYear() - 100 == getCurrentYear();
    }

    public static boolean isToday(Date date) {
        Date todayStart = new Date();
        Date todayEnd = new Date();
        setStartDate(todayStart);
        setEndDate(todayEnd);
        return null != date && date.getTime() >= todayStart.getTime()
                && date.getTime() <= todayEnd.getTime();
    }

    public static Date getYYYYMMDDTHHMMSStoLocalTime(String dateStr) {
        if (dateStr == null || dateStr.trim().length() == 0) {
            return null;
        }
        SimpleDateFormat imDelayFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("GMT+0");
        imDelayFormatter.setTimeZone(timeZone);
        Date date = null;
        try {
            date = imDelayFormatter.parse(dateStr);
        } catch (ParseException e) {
            LogUtil.e(TAG, "[changeUTCtoLocalTime]ParseException", e);
        }
        return date;
    }

    public static String getYYYYMMDDString(Date date) {
        return null == date ? new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                : new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String getYYYYMMDDHHMMSS(Date date) {
        return null == date ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /***/
    public static Date parseSTANDARDFormatToDate(String timeString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeString);
    }

    public static String getHHMMByTimeString(String time) {
        if (time == null) {
            return null;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(time);
        } catch (Exception e) {
            LogUtil.e(TAG, "ParseException:" + e.getMessage());
        }
        if (date == null) {
            return null;
        }
        return getHHMMByDate(date);
    }

    public static String getHHMMByDate(Date date) {
        SimpleDateFormat hourDf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formatTime = hourDf.format(date);
        return formatTime;
    }

    public static String getMMddByDate(Date date) {
        return getFormatStrByDate(date, "MM-dd");
    }

    public static String getFormatStrByDate(Date date, String dateFormat) {
        SimpleDateFormat dayDf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String formatTime = dayDf.format(date);
        return formatTime;
    }

    public static String getComparedTime(String lastTime) {
        Date lastDate = null;
        try {
            lastDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(lastTime);
        } catch (ParseException e) {
            LogUtil.e(TAG, "ParseException:" + e.getMessage());
        }
        if (null == lastDate) {
            return "";
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(lastDate);
        Calendar c = Calendar.getInstance();
        Date nowDate = c.getTime();
        String formatTime = null;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String lastTimeDate = df.format(lastDate);

        if (df.format(nowDate).equals(lastTimeDate)) {
            df = new SimpleDateFormat("HH:mm", Locale.getDefault());
            formatTime = df.format(lastDate);
        } else {
            formatTime = lastTimeDate;
        }
        return formatTime;
    }

    public static String getTimeShowFromUtc(String timeString) {
        String timeShowString = "";
        if (TextUtils.isEmpty(timeString)) {
            return timeShowString;
        }
        timeShowString = DateFormat.format("dd'/'MM'/'yyyy kk':'mm'",
                DateTimeUtil.getYYYYMMDDTHHMMSStoLocalTime(timeString)).toString();
        return timeShowString;
    }

    public String getTimeZone() {
        int offset = TimeZone.getDefault().getRawOffset();
        float timeZone = (float) ((offset * 1.0f) / (1000 * 60 * 60));
        return Float.toString(timeZone);
    }

    /***/
    public static long parseUTCToLong(String strDate) {
        if (strDate == null || strDate.trim().length() == 0) {
            LogUtil.w(TAG, "[parseUTC]strDate is null.");
            return 0;
        }
        long time = 0;
        try {
            LogUtil.i(TAG, "strDate:[" + strDate + "]");
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(strDate);
            time = date.getTime();
            LogUtil.i(TAG, "time:[" + time + "]");
        } catch (ParseException e) {
            LogUtil.e(TAG, "[parseUTCToLong]ParseException", e);
        }
        return time;
    }

    /***/
    public static Date parseUTCTDate(String strDate) {
        if (strDate == null || strDate.trim().length() == 0) {
            LogUtil.w(TAG, "[parseUTC]strDate is null.");
            return null;
        }
        LogUtil.i(TAG, "strDate:[" + strDate + "]");
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(strDate);
        } catch (ParseException e) {
            LogUtil.e(TAG, "[parseUTCToLong]ParseException", e);
        }
        return null;
    }

    /***/
    public static Date parseYYYYMMDDSSToDate(String strDate) {
        if (strDate == null || strDate.trim().length() == 0) {
            LogUtil.w(TAG, "[parseYYYYMMDDSSToDate]strDate is null.");
            return null;
        }

        try {
            return new SimpleDateFormat("yyyyMMddHHmmss").parse(strDate);
        } catch (ParseException e) {
            LogUtil.w(TAG, "[parseYYYYMMDDSSToDate]ParseException", e);
        }

        return null;
    }

    /***/
    public static Date parseSyncUTVToDate(String strDate) {
        if (strDate == null || strDate.trim().length() == 0) {
            LogUtil.w(TAG, "[parseSyncUTVToDate]strDate is null.");
            return null;
        }

        try {
            return new SimpleDateFormat("yyyy'-'MM'-'dd'T00:00:00Z").parse(strDate);
        } catch (ParseException e) {
            LogUtil.w(TAG, "[parseSyncUTVToDate]ParseException", e);
        }

        return null;
    }

    /***/
    public static String changeEmailTimeToUtC(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.length() < "yyyy-MM-ddTHH:mm:ss".length()) {
            return dateStr;
        }

        String time = dateStr.substring(0, "yyyy-MM-ddTHH:mm:ss".length());
        String zoneStr = dateStr.substring("yyyy-MM-ddTHH:mm:ss".length());

        TimeZone timeZone;
        if (zoneStr != null && (zoneStr.contains("+") || zoneStr.contains("-"))) {
            timeZone = TimeZone.getTimeZone("GMT" + zoneStr);
        } else {
            timeZone = TimeZone.getTimeZone("GMT+0");
        }

        SimpleDateFormat localFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        localFormatter.setTimeZone(timeZone);

        Date localDate = localFormatter.parse(time);

        localFormatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));

        return localFormatter.format(localDate);
    }

    public static String getUTCTime() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return DateFormat.format("yyyy'-'MM'-'dd'T'kk':'mm':'ss'Z'", cal).toString();
    }

    public static String getMillisecond() {
        SimpleDateFormat timeStampMilliSecondDF = new SimpleDateFormat("HHmmssSSS");
        return timeStampMilliSecondDF.format(new Date());
    }

    public static String getUTCDteBeforeToday(int days) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.add(Calendar.DAY_OF_YEAR, -days);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy'-'MM'-'dd'T00:00:00Z'");
        String time = formatter.format(cal.getTime());
        return time;
    }

    /***/
    public static Long changeMinToMillSecond(String min) {
        return Long.parseLong(min) * 60 * 1000;
    }

    /***/
    public static Long changeSecondToMillSecond(String second) {
        return Long.parseLong(second) * 1000;
    }

    private static long getYesterdayStart() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DAY_OF_YEAR, -1);
        Date todayDate = ca.getTime();
        return setStartDate(todayDate);
    }

    private static long getYesterdayEnd() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DAY_OF_YEAR, -1);
        Date todayDate = ca.getTime();
        return setEndDate(todayDate);
    }

    private static long getNinedayStart() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DAY_OF_YEAR, -9);
        Date todayDate = ca.getTime();
        return setStartDate(todayDate);
    }

    private static long getNinedayEnd() {
        Calendar ca = Calendar.getInstance();
        Date todayDate = ca.getTime();
        return setEndDate(todayDate);
    }

    private static long setStartDate(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date.getTime();
    }

    private static long setEndDate(Date date) {
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date.getTime();
    }

    public static String getMMSSString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(date);
    }

    public static int getCurrentYear() {
        return new Date().getYear() - 100;
    }

    public static int getCurrentMonth() {
        return new Date().getMonth() + 1;
    }

    public static int getCurrentDay() {
        return new Date().getDate();
    }

    public static int getCurrentHours() {
        return new Date().getHours();
    }

    public static int getCurrentMinutes() {
        return new Date().getMinutes();
    }

    public static int getCurrentSeconds() {
        return new Date().getSeconds();
    }

    public static int getCurrentDayOfWeek() {
        return new Date().getDay();
    }

    public static boolean isValidDate(String sDate, String datePattern1) {
        if (TextUtils.isEmpty(datePattern1)) {
            datePattern1 = "\\d{4}\\d{2}\\d{2}";
        }
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?(]"
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8])))))";
        if (!TextUtils.isEmpty(sDate)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }

    public static String getNowDateStr(String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        return simpleFormat.format(new Date());
    }

    /***/
    public static String parseDate2Str(Date date, String format) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /***/
    public static Date parseStr2Date(String tempDate, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(tempDate);
            return date;
        } catch (Exception e) {
            LogUtil.e(TAG, "parseStr2Date(tempDate): " + e.getMessage());
        }
        return date;
    }

    /***/
    public static String formatDateStr(String time, SimpleDateFormat in, SimpleDateFormat out) {
        if (time == null || in == null | out == null) {
            return time;
        }
        try {
            return out.format(in.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /***/
    public static String formatDateObject(Date date, SimpleDateFormat out) {
        if (date == null || out == null) {
            return null;
        }
        return out.format(date);
    }

    /***/
    public static Date parseDateString(String time, SimpleDateFormat in) {
        if (time == null || in == null) {
            return null;
        }
        try {
            return in.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isTimePassed(String time, String timePattern) {
        SimpleDateFormat df = new SimpleDateFormat(timePattern, Locale.getDefault());
        boolean isPassed = false;
        try {
            Date validDate = df.parse(time);
            Calendar cal = Calendar.getInstance();
            Date currDate = cal.getTime();
            isPassed = validDate.before(currDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        return isPassed;
    }

    public static long getLongTime(String strTime, String timePattern) {
        long longTime = 0;
        if (StringUtil.isNullOrEmpty(strTime) || StringUtil.isNullOrEmpty(timePattern)) {
            LogUtil.w(TAG, "getLongTime, strTime:" + strTime + ", timePattern:" + timePattern);
            return longTime;
        }
        SimpleDateFormat df = new SimpleDateFormat(timePattern, Locale.getDefault());
        try {
            longTime = df.parse(strTime).getTime();
        } catch (Exception e) {
            LogUtil.e(TAG, "getLongTime, Exception occurs strTime:" + strTime + ", timePattern:"
                    + timePattern);
        }
        return longTime;
    }
}
