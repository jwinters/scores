package io.lose.scores.operations;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;

import java.util.Map;

import io.lose.scores.application.LoseApi;
import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.BoxScoreTable;
import io.lose.scores.utils.DataUtils;
import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.SimpleOperation;
import io.pivotal.arca.threading.Identifier;

public class GetBoxScoreOperation extends SimpleOperation {

	private final String mBoxScoreId;

	public GetBoxScoreOperation(final String id) {
		super(ScoresContentProvider.Uris.BOX_SCORES);
		mBoxScoreId = id;
	}

	private GetBoxScoreOperation(final Parcel in) {
		super(in);
		mBoxScoreId = in.readString();
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(mBoxScoreId);
	}

	@Override
	public Identifier<?> onCreateIdentifier() {
		return new Identifier<>("BOX_SCORE:" + mBoxScoreId);
	}

	@Override
	public ContentValues[] onExecute(final Context context) throws Exception {
		final Map<String, String> item = LoseApi.getBoxScore(mBoxScoreId);
		return new ContentValues[] { DataUtils.get(BoxScoreTable.class, item) };
	}

    @Override
    public void onPostExecute(final Context context, final ContentValues[] values) throws Exception {
		context.getContentResolver().bulkInsert(getUri(), values);
        context.getContentResolver().notifyChange(ScoresContentProvider.Uris.SCORES, null);
		context.getContentResolver().notifyChange(ScoresContentProvider.Uris.BOX_SCORES, null);
    }

	@Override
	public void onComplete(final Context context, final Results results) {
		if (results.hasFailedTasks()) {
			final ServiceError error = results.getFailedTasks().get(0).getError();
			ErrorBroadcaster.broadcast(context, getUri(), error.getCode(), error.getMessage());
		}
	}

	public static final Creator CREATOR = new Creator() {
		@Override
		public GetBoxScoreOperation createFromParcel(final Parcel in) {
			return new GetBoxScoreOperation(in);
		}

		@Override
		public GetBoxScoreOperation[] newArray(final int size) {
			return new GetBoxScoreOperation[size];
		}
	};
}