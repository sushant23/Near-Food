package com.tr.nearfood.activity;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;

import com.tr.nearfood.R;
import com.tyczj.extendedcalendarview.CalendarProvider;
import com.tyczj.extendedcalendarview.Event;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

public class CustumCalenderEvents extends Activity {

	int startTimeHour=0;
	int startDayYear=2013;
	int startTimeMin=0;
	int startDayDay=1;
	int startDayMonth=1;
	
	
	int endTimeMin=60;
	int endTimeHour=24;
	int endDayYear=2020;
	int endDayDay=365;
	int endDayMonth=12;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_calender);
		ExtendedCalendarView calendar = (ExtendedCalendarView) findViewById(R.id.calendar);
		addEvents();
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(CalendarProvider.COLOR, Event.COLOR_RED);
		values.put(CalendarProvider.DESCRIPTION, "Some Description");
		values.put(CalendarProvider.LOCATION, "Some location");
		values.put(CalendarProvider.EVENT, "Event name");

		Calendar cal = Calendar.getInstance();

		cal.set(startDayYear, startDayMonth, startDayDay, startTimeHour,
				startTimeMin);
		values.put(CalendarProvider.START, cal.getTimeInMillis());
		String julianDay="";
		//values.put(CalendarProvider.START_DAY, julianDay);
		TimeZone tz = TimeZone.getDefault();

	
		cal.set(endDayYear, endDayMonth, endDayDay, endTimeHour, endTimeMin);
		int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(),
				TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal
						.getTimeInMillis())));

		values.put(CalendarProvider.END, cal.getTimeInMillis());
		values.put(CalendarProvider.END_DAY, endDayJulian);

		Uri uri = getContentResolver().insert(CalendarProvider.CONTENT_URI,
				values);
	}
}
