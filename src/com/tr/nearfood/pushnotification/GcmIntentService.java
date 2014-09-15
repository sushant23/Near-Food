package com.tr.nearfood.pushnotification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import static com.tr.nearfood.pushnotification.CommonUtilities.sendMessage;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tr.nearfood.R;
import com.tr.nearfood.dbhelper.DatabaseHelper;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	static final String TAG = "GCMDemo";
	Context context = this;
	DatabaseHelper db;

	public static String eventID;
	public SharedPreferences NOTIFICATION;
	public static final String MyPREFERENCES = "MyPrefs";
	public static int badgeValue=0;
	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		NOTIFICATION = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		db = new DatabaseHelper(context);

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// This loop represents the service doing some work.
				for (int i = 0; i < 2; i++) {
					Log.i(TAG,
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				Editor editor = NOTIFICATION.edit();
				editor.putBoolean("badge",true);
				editor.putInt("badgeValue",badgeValue++ );
				editor.commit();
				
				Bundle b = extras;
				String message = b.getString("gcm");
				sendNotification(message);
				sendMessage(context, message);
				Log.i(TAG, "Received: " + extras.toString() + "meassage="
						+ message+ "badgeValue="+Integer.toString(badgeValue));
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(String msg) {
		int icon = R.drawable.icon_near_food;
		long when = System.currentTimeMillis();
		Log.d("notification message", msg);

		String[] separated = msg.split(":");
		eventID = separated[0];
		String message = separated[1];
		String noti_message=separated[2];
		Log.d("NOTIFATAIN message", noti_message);
		db.createNotifiaction(noti_message);
		db.close();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);

		String title = context.getString(R.string.app_name);

		Intent notificationIntent = new Intent(context,
				CalandarEventActivity.class);
		notificationIntent.putExtra("eventID", eventID);
		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Play default notification sound
		notification.defaults |= Notification.DEFAULT_SOUND;

		// notification.sound = Uri.parse("android.resource://" +
		// context.getPackageName() + "your_sound_file_name.mp3");

		// Vibrate if vibrate is enabled
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);
	}
}