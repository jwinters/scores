package io.elapse.scores.operations;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.service.Operation;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.Task;

public class GetArticleListOperation extends Operation {

	private final String mId;

	public GetArticleListOperation(final Uri uri, final String id) {
		super(uri);
		mId = id;
	}

	public GetArticleListOperation(final Parcel in) {
		super(in);
		mId = in.readString();
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(mId);
	}

	@Override
	public Set<Task<?>> onCreateTasks() {
		final Set<Task<?>> set = new HashSet<Task<?>>();
		set.add(new GetArticleListTask(mId));
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

	public static final Creator CREATOR = new Creator() {
		@Override
		public GetArticleListOperation createFromParcel(final Parcel in) {
			return new GetArticleListOperation(in);
		}

		@Override
		public GetArticleListOperation[] newArray(final int size) {
			return new GetArticleListOperation[size];
		}
	};
}