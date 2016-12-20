package io.lose.scores.operations;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;

import java.util.List;
import java.util.Map;

import io.lose.scores.application.LoseApi;
import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.datasets.ArticleTable;
import io.lose.scores.utils.DataUtils;
import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.SimpleOperation;
import io.pivotal.arca.threading.Identifier;

public class GetArticlesOperation extends SimpleOperation {

	public GetArticlesOperation() {
		super(ScoresContentProvider.Uris.ARTICLES);
	}

	private GetArticlesOperation(final Parcel in) {
		super(in);
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		super.writeToParcel(dest, flags);
	}

	@Override
	public Identifier<?> onCreateIdentifier() {
		return new Identifier<>("ARTICLES");
	}

	@Override
	public ContentValues[] onExecute(final Context context) throws Exception {
		final List<Map<String, String>> item = LoseApi.getArticles();
		return DataUtils.get(ArticleTable.class, item);
	}

    @Override
    public void onPostExecute(final Context context, final ContentValues[] values) throws Exception {
		context.getContentResolver().bulkInsert(getUri(), values);
        context.getContentResolver().notifyChange(ScoresContentProvider.Uris.ARTICLES, null);
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
		public GetArticlesOperation createFromParcel(final Parcel in) {
			return new GetArticlesOperation(in);
		}

		@Override
		public GetArticlesOperation[] newArray(final int size) {
			return new GetArticlesOperation[size];
		}
	};
}