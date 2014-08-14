package com.tr.nearfood.utills;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

import com.sromku.simple.fb.utils.Logger;

import android.app.Application;

public class FaceBookUtills extends Application {
	private static final String APP_ID = "1384264938499969";
	private static final String APP_NAMESPACE = "near_food";

	@Override
	public void onCreate() {
		super.onCreate();

		// set log to true
		Logger.DEBUG_WITH_STACKTRACE = true;

		// initialize facebook configuration
		Permission[] permissions = new Permission[] {
				Permission.PUBLIC_PROFILE};

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(APP_ID).setNamespace(APP_NAMESPACE)
				.setPermissions(permissions)
				.setDefaultAudience(SessionDefaultAudience.FRIENDS)
				.setAskForAllPermissionsAtOnce(false).build();

		SimpleFacebook.setConfiguration(configuration);
	}
}
