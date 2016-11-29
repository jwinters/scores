package io.lose.scores.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.iid.FirebaseInstanceId;

import io.lose.scores.utils.Logger;

public class ScoresApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Fresco.initialize(this);

		Logger.v("token: " +  FirebaseInstanceId.getInstance().getToken());
	}
}