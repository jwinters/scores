package com.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.scores.application.ScoresApi;
import com.scores.models.BoxScoreResponse;
import com.scores.providers.ScoresContentProvider;

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
        final BoxScoreResponse list = ScoresApi.getBoxscore(mBoxScoreId);
        return list;
    }

    @Override
    public void onExecuteProcessing(final Context context, final BoxScoreResponse response) throws Exception {
        final ContentResolver resolver = context.getContentResolver();

        final ContentValues boxScoreValues = DataUtils.getContentValues(response);
        resolver.insert(ScoresContentProvider.Uris.BOX_SCORES, boxScoreValues);
    }

}
