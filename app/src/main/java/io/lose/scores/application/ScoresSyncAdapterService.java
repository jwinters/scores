package io.lose.scores.application;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

import io.lose.scores.operations.GetGameListOperation;
import io.pivotal.arca.service.OperationService;

public class ScoresSyncAdapterService extends Service {

	private ScoresSyncAdapter mSyncAdapter = new ScoresSyncAdapter(this);

	@Override
	public IBinder onBind(Intent intent) {
		return mSyncAdapter.getSyncAdapterBinder();
	}

	public static class ScoresSyncAdapter extends AbstractThreadedSyncAdapter {

		private ScoresSyncAdapter(final Context context) {
			super(context, true);
		}

		@Override
		public void onPerformSync(final Account account, final Bundle extras, final String authority, final ContentProviderClient provider, final SyncResult syncResult) {

			final Uri uri = ScoresContentProvider.Uris.SCORES;
			final String date = ISODateTimeFormat.date().print(LocalDateTime.now());

			OperationService.start(getContext(), new GetGameListOperation(uri, date));
		}
	}

}