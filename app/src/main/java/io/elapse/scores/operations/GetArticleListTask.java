package io.elapse.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import io.elapse.scores.application.ScoresApi;
import io.elapse.scores.application.ScoresContentProvider;
import io.elapse.scores.models.ArticleListResponse;
import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.service.Task;
import io.pivotal.arca.threading.Identifier;

public class GetArticleListTask extends Task<ArticleListResponse> {

	private final String mId;

	public GetArticleListTask(final String id) {
		mId = id;
	}

	@Override
	public Identifier<?> onCreateIdentifier() {
		return new Identifier<String>("get_article_list:" + mId);
	}
	
	@Override
	public ArticleListResponse onExecuteNetworking(final Context context) throws Exception {
        return ScoresApi.getArticleList(mId);
	}

	@Override
	public void onExecuteProcessing(final Context context, final ArticleListResponse response) throws Exception {
		final ContentResolver resolver = context.getContentResolver();

        final ContentValues[] teamValues = DataUtils.getContentValues(response.getArticles());
		resolver.bulkInsert(ScoresContentProvider.Uris.ARTICLES, teamValues);
	}
}