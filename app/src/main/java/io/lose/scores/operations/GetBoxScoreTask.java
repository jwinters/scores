package io.lose.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import io.lose.scores.application.ScoresApi;
import io.lose.scores.models.BoxScoreResponse;
import io.lose.scores.application.ScoresContentProvider;

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
        return ScoresApi.getBoxScore(mBoxScoreId);
    }

    @Override
    public void onExecuteProcessing(final Context context, final BoxScoreResponse response) throws Exception {
        final ContentResolver resolver = context.getContentResolver();

        final ContentValues boxScoreValues = DataUtils.getContentValues(response);
        resolver.insert(ScoresContentProvider.Uris.BOX_SCORES, boxScoreValues);
    }

}
