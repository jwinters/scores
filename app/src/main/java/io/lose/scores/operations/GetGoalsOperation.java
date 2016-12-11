package io.lose.scores.operations;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;

import java.util.List;
import java.util.Map;

import io.lose.scores.application.LoseApi;
import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.GoalTable;
import io.lose.scores.utils.DataUtils;
import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.SimpleOperation;
import io.pivotal.arca.threading.Identifier;

public class GetGoalsOperation extends SimpleOperation {

	private final String mBoxScoreId;

	public GetGoalsOperation(final String id) {
		super(ScoresContentProvider.Uris.GOALS);
		mBoxScoreId = id;
	}

	private GetGoalsOperation(final Parcel in) {
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
		return new Identifier<>("GOALS:" + mBoxScoreId);
	}

	@Override
	public ContentValues[] onExecute(final Context context) throws Exception {
		final List<Map<String, String>> list = LoseApi.getGoals(mBoxScoreId);
        return DataUtils.get(GoalTable.class, list);
	}

    @Override
    public void onPostExecute(final Context context, final ContentValues[] values) throws Exception {
        final String where = GoalTable.Columns.BOX_SCORE_ID + "=?";
        final String[] whereArgs = new String[] { String.valueOf(mBoxScoreId) };

        context.getContentResolver().delete(getUri(), where, whereArgs);
        context.getContentResolver().bulkInsert(getUri(), values);
        context.getContentResolver().notifyChange(ScoresContentProvider.Uris.SCORING, null);
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
		public GetGoalsOperation createFromParcel(final Parcel in) {
			return new GetGoalsOperation(in);
		}

		@Override
		public GetGoalsOperation[] newArray(final int size) {
			return new GetGoalsOperation[size];
		}
	};
}