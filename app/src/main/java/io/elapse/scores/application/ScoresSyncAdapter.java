package io.elapse.scores.application;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;

import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

import io.elapse.scores.operations.GetGameListOperation;
import io.pivotal.arca.service.OperationService;

public class ScoresSyncAdapter extends AbstractThreadedSyncAdapter {

	public ScoresSyncAdapter(final Context context, final boolean autoInitialize) {
		super(context, autoInitialize);
	}

	@Override
	public void onPerformSync(final Account account, final Bundle extras, final String authority, final ContentProviderClient provider, final SyncResult syncResult) {

        final Uri uri = ScoresContentProvider.Uris.SCORES;
		final String date = ISODateTimeFormat.date().print(LocalDateTime.now());

        OperationService.start(getContext(), new GetGameListOperation(uri, date));
	}

}