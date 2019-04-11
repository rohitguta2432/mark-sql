package com.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Slf4j
public class TheamlyfeUtility {

	public boolean findString(String commaSapratedString, String key) {

		String[] findString = commaSapratedString.split(",");
		return Arrays.asList(findString).contains(key);
	}

	public Date getDateFromString(String dateString) {
		// String dateString = "Tue Sep 12 14:15:08 IST 2017"; Sample format

		if (StringUtils.isEmpty(dateString)) {
			log.info("Error while converting String into Date.");
		}

		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			date = formatter.parse(getDateString(dateString));

			return date;
		} catch (Exception e) {
			log.info("Error while converting String into Date.");
		}
		return null;
	}

	public String getDateString(String string) {

		/*
		 * Format should be String string = "Tue Sep 12 14:15:08 IST 2017";
		 */
		try {

			String year = string.substring((string.length() - 4));
			String month = string.substring(4, 7);
			String day = string.substring(8, 10);
			String time = string.substring(11, 19);
			return day + " " + month + " " + year + " " + time;
		} catch (Exception e) {
			log.info("Error while converting String(Tue Sep 12 14:15:08 IST 2017) into DateString.");
		}
		return null;
	}

	public String getUrlEncoded(String url) {
		try {
			url = URLEncoder.encode(url, "UTF-8");
			return url.replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public boolean isAlphaNumeric(String s) {
		String pattern = "^[a-zA-Z0-9]*$";
		return s.matches(pattern);
	}
}
