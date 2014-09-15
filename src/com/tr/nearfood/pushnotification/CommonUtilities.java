package com.tr.nearfood.pushnotification;

import android.content.Context;
import android.content.Intent;

public class CommonUtilities {
	static final String DISPLAY_MESSAGE_ACTION =
            "com.tr.nearfood.pushnotification.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

	static void sendMessage(Context context, String message) {
	        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
	        intent.putExtra(EXTRA_MESSAGE, message);
	        context.sendBroadcast(intent);
	    }
}
