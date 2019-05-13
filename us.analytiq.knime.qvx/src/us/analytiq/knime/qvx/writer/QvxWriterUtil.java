package us.analytiq.knime.qvx.writer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.knime.core.node.NodeLogger;

public class QvxWriterUtil {
	
    private static final NodeLogger LOGGER = NodeLogger.getLogger(QvxWriterUtil.class);

	private static final long MILLISECONDS_PER_DAY = 86400000;
	private static final double SECONDS_PER_DAY = 86400;
	private static Date epoch;
	private static Date startDate;
	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("EDT"));
		try {
			epoch = dateFormat.parse("1 1 1970");
			startDate = dateFormat.parse("12 30 1899");
		}catch(ParseException e) {
			LOGGER.error("Error writing qvx file; could not parse date");
		}
	}
	
	private QvxWriterUtil() {
		// Hides the implicit private constructor (squid:S1118)
	}
	
	public static byte[] combineByteArrays(byte[] a, byte[] b) {
		
		byte[] returnValue = new byte[a.length + b.length];
		for(int i = 0; i < a.length; i++) {
			returnValue[i] = a[i];
		}
		for(int i = 0; i < b.length; i++) {
			returnValue[a.length + i] = b[i];
		}
		return returnValue;
	}
	
	public static double dateToDaysSince(String day) {
		
		String[] parts = day.split("-");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1])-1;
		int dayOfMonth= Integer.parseInt(parts[2]);
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EDT"));
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		cal.set(Calendar.YEAR, year);
		resolveDateOffset(cal);
				
		return (cal.getTimeInMillis() + epoch.getTime() - startDate.getTime())/MILLISECONDS_PER_DAY;
	}
	
	public static double timeToDaysSince(String time) {
				
		String[] parts = time.split(":");
		double hours = Double.parseDouble(parts[0]);
		double minutes = Double.parseDouble(parts[1]);
		
		double seconds = 0;
		try {
			seconds = Double.parseDouble(parts[2]);
		}catch(IndexOutOfBoundsException e) { //If only minutes and hours are specified
			seconds = 0;
		}
		
		return (hours*3600 + minutes*60 + seconds)/SECONDS_PER_DAY;
	}
		
	public static String removeSuffix(String s, String suffix) {
		if (s.endsWith(suffix)) {
			return s.substring(0, s.lastIndexOf(suffix));
		}else {
			return s;
		}
	}
	
	public static void resolveDateOffset(Calendar cal) {
		
		int year = cal.get(Calendar.YEAR);
		int dayOfMonth= cal.get(Calendar.DAY_OF_MONTH);
		
		if (year < 1900 && year > 1) {
			//If the date is before 1900, the calendar will be off by one day
			cal.set(Calendar.DAY_OF_MONTH, dayOfMonth - 1);
		}		
	}
	
	public static String toTitleCase(String s) {
		// Return a copy of s with the first letter capitalized
		
		if (s.length() == 0) {
			return s;
		}
		
		return ("" + s.charAt(0)).toUpperCase() + s.substring(1, s.length());
	}
}
