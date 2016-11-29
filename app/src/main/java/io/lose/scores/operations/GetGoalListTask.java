package io.lose.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import io.lose.scores.application.ScoresApi;
import io.lose.scores.datasets.GoalTable;
import io.lose.scores.models.GoalListResponse;
import io.lose.scores.application.ScoresContentProvider;

import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.service.Task;
import io.pivotal.arca.threading.Identifier;

public class GetGoalListTask extends Task<GoalListResponse> {

    private final long mBoxScoreId;

    public GetGoalListTask(final long boxScoreId) {
        mBoxScoreId = boxScoreId;
    }

    @Override
    public Identifier<?> onCreateIdentifier() {
        return new Identifier<String>("get_goal_list:" + mBoxScoreId);
    }

    @Override
    public GoalListResponse onExecuteNetworking(final Context context) throws Exception {
        return ScoresApi.getGoalList(mBoxScoreId);
    }

    @Override
    public void onExecuteProcessing(final Context context, final GoalListResponse response) throws Exception {
        final ContentValues values = new ContentValues();
        values.put(GoalTable.Columns.BOX_SCORE_ID, mBoxScoreId);
        final ContentResolver resolver = context.getContentResolver();

        final String where = GoalTable.Columns.BOX_SCORE_ID + "=?";
        final String[] whereArgs = new String[] { String.valueOf(mBoxScoreId) };
        resolver.delete(ScoresContentProvider.Uris.GOALS, where, whereArgs);

        final ContentValues[] goalValues = DataUtils.getContentValues(response, values);
        resolver.bulkInsert(ScoresContentProvider.Uris.GOALS, goalValues);
    }

}
