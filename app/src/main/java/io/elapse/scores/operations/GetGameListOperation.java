package io.elapse.scores.operations;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.service.Operation;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.Task;

public class GetGameListOperation extends Operation {

	private final String mDate;
	
	public GetGameListOperation(final Uri uri, final String date) {
		super(uri);
		mDate = date;
	}

	public GetGameListOperation(final Parcel in) {
		super(in);
		mDate = in.readString();
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(mDate);
	}

	@Override
	public Set<Task<?>> onCreateTasks() {
		final Set<Task<?>> set = new HashSet<Task<?>>();
		set.add(new GetGameListTask(mDate));
		return set;
	}

	@Override
	public void onSuccess(final Context context, final List<Task<?>> completed) {
		final ContentResolver resolver = context.getContentResolver();
		resolver.notifyChange(getUri(), null);
	}

	@Override
	public void onFailure(final Context context, final ServiceError error) {
		ErrorBroadcaster.broadcast(context, getUri(), error.getCode(), error.getMessage());
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		@Override
		public GetGameListOperation createFromParcel(final Parcel in) {
			return new GetGameListOperation(in);
		}

		@Override
		public GetGameListOperation[] newArray(final int size) {
			return new GetGameListOperation[size];
		}
	};
}