package io.elapse.scores.application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class ScoresSyncAdapterService extends Service {

	private static ScoresSyncAdapter sSyncAdapter;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		synchronized (this) {
			if (sSyncAdapter == null) {
				final Context context = getApplicationContext();
				sSyncAdapter = new ScoresSyncAdapter(context, true);
			}
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return sSyncAdapter.getSyncAdapterBinder();
	}

}