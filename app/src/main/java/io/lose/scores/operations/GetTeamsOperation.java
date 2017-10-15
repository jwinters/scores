package io.lose.scores.operations;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;

import java.util.List;
import java.util.Map;

import io.lose.scores.application.ScoresApi;
import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.TeamTable;
import io.lose.scores.utils.DataUtils;
import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.SimpleOperation;
import io.pivotal.arca.threading.Identifier;

public class GetTeamsOperation extends SimpleOperation {

	public GetTeamsOperation() {
		super(ScoresContentProvider.Uris.TEAMS);
	}

	private GetTeamsOperation(final Parcel in) {
		super(in);
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		super.writeToParcel(dest, flags);
	}

	@Override
	public Identifier<?> onCreateIdentifier() {
		return new Identifier<>("TEAMS");
	}

	@Override
	public ContentValues[] onExecute(final Context context) throws Exception {
		final List<Map<String, String>> item = ScoresApi.getTeams();
		return DataUtils.get(TeamTable.class, item);
	}

    @Override
    public void onPostExecute(final Context context, final ContentValues[] values) throws Exception {
		context.getContentResolver().bulkInsert(getUri(), values);
        context.getContentResolver().notifyChange(ScoresContentProvider.Uris.TEAMS, null);
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
		public GetTeamsOperation createFromParcel(final Parcel in) {
			return new GetTeamsOperation(in);
		}

		@Override
		public GetTeamsOperation[] newArray(final int size) {
			return new GetTeamsOperation[size];
		}
	};
}