package io.elapse.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import io.elapse.scores.application.ScoresApi;
import io.elapse.scores.models.BoxScoreResponse;
import io.elapse.scores.application.ScoresContentProvider;

import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.service.Task;
import io.pivotal.arca.threading.Identifier;

public class GetBoxScoreTask extends Task<BoxScoreResponse> {

    private final long mBoxScoreId;

    public GetBoxScoreTask(final long boxScoreId) {
        mBoxScoreId = boxScoreId;
    }

    @Override
    public Identifier<?> onCreateIdentifier() {
        return new Identifier<String>("get_box_score:" + mBoxScoreId);
    }

    @Override
    public BoxScoreResponse onExecuteNetworking(final Context context) throws Exception {
        final BoxScoreResponse list = ScoresApi.getBoxScore(mBoxScoreId);
        return list;
    }

    @Override
    public void onExecuteProcessing(final Context context, final BoxScoreResponse response) throws Exception {
        final ContentResolver resolver = context.getContentResolver();

        final ContentValues boxScoreValues = DataUtils.getContentValues(response);
        resolver.insert(ScoresContentProvider.Uris.BOX_SCORES, boxScoreValues);
    }

}
