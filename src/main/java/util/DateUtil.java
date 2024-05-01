package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat instance;
	
	private static SimpleDateFormat getInstance() {
		if(instance == null) {
			return new SimpleDateFormat("dd/MM/yyyy");
		}
		return instance;
	}
	
	public static Date fromStringToDate(String dateString) throws ParseException {
		return getInstance().parse(dateString);
	}
	
	public static String fromDateToString(Date date) throws ParseException {
		return getInstance().format(date);
	}
	
}
