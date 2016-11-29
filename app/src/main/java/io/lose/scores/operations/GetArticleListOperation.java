package io.lose.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;

import io.lose.scores.application.ScoresApi;
import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.models.ArticleListResponse;
import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.service.Operation;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.SimpleOperation;

public class GetArticleListOperation extends SimpleOperation {

	private final String mId;

	public GetArticleListOperation(final Uri uri, final String id) {
		super(uri);
		mId = id;
	}

	private GetArticleListOperation(final Parcel in) {
		super(in);
		mId = in.readString();
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(mId);
	}

	@Override
	public ContentValues[] onExecute(final Context context) throws Exception {
		final ArticleListResponse response = ScoresApi.getArticleList(mId);
        return DataUtils.getContentValues(response.getArticles());
	}

	@Override
	public void onPostExecute(final Context context, final ContentValues[] values) throws Exception {
		final ContentResolver resolver = context.getContentResolver();
		resolver.bulkInsert(ScoresContentProvider.Uris.ARTICLES, values);
	}

	@Override
	public void onComplete(final Context context, final Operation.Results results) {
		if (results.hasFailedTasks()) {
			final ServiceError error = results.getFailedTasks().get(0).getError();
			ErrorBroadcaster.broadcast(context, getUri(), error.getCode(), error.getMessage());
		}
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