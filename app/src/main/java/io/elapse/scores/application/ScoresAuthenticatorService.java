package io.elapse.scores.application;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ScoresAuthenticatorService extends Service {

	private ScoresAuthenticator mAuthenticator;

	@Override
	public void onCreate() {
		super.onCreate();
		
		if (mAuthenticator == null) { 
			mAuthenticator = new ScoresAuthenticator(this);
		}
	}
	
	@Override
	public IBinder onBind(final Intent intent) {
		return mAuthenticator.getIBinder();
	}
}