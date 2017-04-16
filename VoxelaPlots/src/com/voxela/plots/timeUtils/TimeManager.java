package com.voxela.plots.timeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import com.voxela.plots.Main;

public class TimeManager {
	
	private static int numberOfDaysInMonth(int month, int year) {
	    Calendar monthStart = new GregorianCalendar(year, month, 1);
	    return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static String timeNow() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
		String time = sdf.format(Calendar.getInstance().getTime());
		String[] timeString = time.split(":");
		
		int year = Integer.parseInt(timeString[0]);
		int month = Integer.parseInt(timeString[1]);
		int date = Integer.parseInt(timeString[2]);
		int hour = Integer.parseInt(timeString[3]);
		int minute = Integer.parseInt(timeString[4]);
		int second = Integer.parseInt(timeString[5]);
		
		String timeRaw = Integer.toString(year) + ":" + Integer.toString(month) + ":" + Integer.toString(date) + ":" + 
				Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second);
		return timeRaw;

	}
	
	public static String timePlusWeek() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss:D");
		String time = sdf.format(Calendar.getInstance().getTime());
		String[] timeString = time.split(":");
		
		int year = Integer.parseInt(timeString[0]);
		int month = Integer.parseInt(timeString[1]);
		int date = Integer.parseInt(timeString[2]);
		int hour = Integer.parseInt(timeString[3]);
		int minute = Integer.parseInt(timeString[4]);
		int second = Integer.parseInt(timeString[5]);
		int dayInYear = Integer.parseInt(timeString[6]);
		
		if (365 < dayInYear + 7) {
			
			int nextYear = year +1;
			int nextMonth = 1;
			int nextDate = date + 7 - numberOfDaysInMonth(month, year);
			
			String timeRaw = Integer.toString(nextYear) + ":" + Integer.toString(nextMonth) + ":" + Integer.toString(nextDate) + ":" + 
					Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second);
			return timeRaw;
			
		}
		
		if (numberOfDaysInMonth(month, year) < date + 7) {
			
			int nextMonth = month + 1;
			int nextDate = date + 7 - numberOfDaysInMonth(month, year);
			
			String timeRaw = Integer.toString(year) + ":" + Integer.toString(nextMonth) + ":" + Integer.toString(nextDate) + ":" + 
					Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second);
			return timeRaw;
			
		} else {
			
			int nextDate = date + 7;
			
			String timeRaw = Integer.toString(year) + ":" + Integer.toString(month) + ":" + Integer.toString(nextDate) + ":" + 
					Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second);
			return timeRaw;
			
		}
	}
	
	public static String timeFormatter(String timeRaw) {
				
        SimpleDateFormat sdfUnformatted = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        Date dateRaw;
        String sdfFormatted = null;
        
		try {
			dateRaw = sdfUnformatted.parse(timeRaw);
	        sdfUnformatted.applyPattern("EEEE, MMMMM d, yyyy 'at' hh:mm aaa" );
	        sdfFormatted = sdfUnformatted.format(dateRaw);
		} catch (ParseException e) {
			System.out.print(Main.consolePrefix + "Error while trying to parse date:" + timeRaw);
			e.printStackTrace();
		}
		
		return sdfFormatted;
		
	}
}

// Player rents plot
// Get current time
// Add 7 days to current time
// Check hourly for all plots