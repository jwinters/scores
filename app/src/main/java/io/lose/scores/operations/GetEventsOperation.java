package io.lose.scores.operations;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;
import java.util.Map;

import io.lose.scores.application.LoseApi;
import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.EventTable;
import io.lose.scores.utils.DataUtils;
import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.SimpleOperation;
import io.pivotal.arca.threading.Identifier;

public class GetEventsOperation extends SimpleOperation {

	private final String mDate;
	
	public GetEventsOperation(final String date) {
		super(ScoresContentProvider.Uris.EVENTS);
		mDate = date;
	}

	private GetEventsOperation(final Parcel in) {
		super(in);
		mDate = in.readString();
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(mDate);
	}

	@Override
	public Identifier<?> onCreateIdentifier() {
		return new Identifier<>("EVENTS:" + mDate);
	}

	@Override
	public ContentValues[] onExecute(final Context context) throws Exception {
		final DateTime time = ISODateTimeFormat.dateTimeParser().parseDateTime(mDate);
		final List<Map<String, String>> list = LoseApi.getEvents(time);
        return DataUtils.get(EventTable.class, list);
	}

    @Override
    public void onPostExecute(final Context context, final ContentValues[] values) throws Exception {
        context.getContentResolver().bulkInsert(getUri(), values);
        context.getContentResolver().notifyChange(ScoresContentProvider.Uris.SCORES, null);
    }

	@Override
	public void onComplete(final Context context, final Results results) {
		if (results.hasFailedTasks()) {
			final ServiceError error = results.getFailedTasks().get(0).getError();
			ErrorBroadcaster.broadcast(context, getUri(), error.getCode(), error.getMessage());
		}
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		@Override
		public GetEventsOperation createFromParcel(final Parcel in) {
			return new GetEventsOperation(in);
		}

		@Override
		public GetEventsOperation[] newArray(final int size) {
			return new GetEventsOperation[size];
		}
	};
}