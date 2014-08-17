package com.tr.nearfood.utills;

import android.app.Application;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.tr.nearfood.utills.SharedObjects;
import com.sromku.simple.fb.utils.Logger;

public class SampleApplication extends Application {
	private static final String APP_ID = "1384264938499969";
	private static final String APP_NAMESPACE = "near_food";

	@Override
	public void onCreate() {
		super.onCreate();
		SharedObjects.context = this;

		// set log to true
		Logger.DEBUG_WITH_STACKTRACE = true;

		// initialize facebook configuration
		Permission[] permissions = new Permission[] {
				Permission.PUBLIC_PROFILE, Permission.READ_STREAM,
				Permission.PUBLISH_ACTION };

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(APP_ID).setNamespace(APP_NAMESPACE)
				.setPermissions(permissions)
				.setDefaultAudience(SessionDefaultAudience.FRIENDS)
				.setAskForAllPermissionsAtOnce(false).build();

		SimpleFacebook.setConfiguration(configuration);
	}
}
