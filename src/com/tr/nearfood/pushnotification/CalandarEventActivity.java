package com.tr.nearfood.pushnotification;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.util.Log;
import static com.tr.nearfood.pushnotification.GcmIntentService.eventID;
public class CalandarEventActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	//	String eventID= getIntent().getStringExtra("eventID");
	//	Log.d("event to dispalt", eventID);
		/*Intent intent = new Intent(Intent.ACTION_VIEW);
		//Android 2.2+
		intent.setData(Uri.parse("content://com.android.calendar/events/" + eventID));  
		//Android 2.1 and below.
		//intent.setData(Uri.parse("content://calendar/events/" + String.valueOf(calendarEventID)));    
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
		        | Intent.FLAG_ACTIVITY_SINGLE_TOP
		        | Intent.FLAG_ACTIVITY_CLEAR_TOP
		        | Intent.FLAG_ACTIVITY_NO_HISTORY
		        | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		startActivity(intent);*/
		
		Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, Long.parseLong(eventID));
		Intent intent = new Intent(Intent.ACTION_VIEW)
		   .setData(uri);
		startActivity(intent);
	}

}
