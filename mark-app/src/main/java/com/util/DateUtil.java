package com.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import com.web.MarkWebException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Slf4j
@Component
public class DateUtil {

	public static long getCurrentTimeInMilliseconds() {
		return System.currentTimeMillis();
	}

	public static Date getDate(String date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			log.debug("Invalid date format " + date);
			return null;
		}
	}

	public static boolean isValidDate(String dateString, String pattern) {
		Date date = getDate(dateString, pattern);
		if (date == null) {
			return false;
		}
		return true;
	}

	public static List<Date> splitDuration(Date startDate, Date endDate, long splitSize) {
		long startMillis = startDate.getTime();
		long endMillis = endDate.getTime();
		List<Date> list = new ArrayList<Date>();
		while (startMillis <= endMillis) {
			list.add(new Date(startMillis));
			startMillis += splitSize;
		}
		return list;
	}

	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static String getMonthName(int monthId) {
		return new DateFormatSymbols().getMonths()[monthId];
	}

	

	public static int getCurrentYear() {

		try {
			Calendar now = Calendar.getInstance();
			return now.get(Calendar.YEAR);

		} catch (Exception e) {
			log.info("Error while fetching current year.");
			throw new MarkWebException("Error while fetching current year.");
		}
	}

	public static int getCurrentMonth() {

		try {
			Calendar now = Calendar.getInstance();
			return now.get(Calendar.MONTH);
		} catch (Exception e) {
			log.info("Error while fetching current month.");
			throw new MarkWebException("Error while fetching current month.");
		}
	}

}
