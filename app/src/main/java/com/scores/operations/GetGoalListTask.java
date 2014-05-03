package com.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.scores.application.ScoresApi;
import com.scores.datasets.GoalTable;
import com.scores.models.GoalListResponse;
import com.scores.providers.ScoresContentProvider;

import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.service.Task;
import io.pivotal.arca.threading.Identifier;

public class GetGoalListTask extends Task<GoalListResponse> {

    private final long mGameId;
    private final long mBoxScoreId;

    public GetGoalListTask(final long gameId, final long boxScoreId) {
        mGameId = gameId;
        mBoxScoreId = boxScoreId;
    }

    @Override
    public Identifier<?> onCreateIdentifier() {
        return new Identifier<String>("get_goal_list:" + mBoxScoreId);
    }

    @Override
    public GoalListResponse onExecuteNetworking(final Context context) throws Exception {
        final GoalListResponse list = ScoresApi.getGoalList(mBoxScoreId);
        return list;
    }

    @Override
    public void onExecuteProcessing(final Context context, final GoalListResponse response) throws Exception {
        final ContentValues values = new ContentValues();
        values.put(GoalTable.Columns.GAME_ID, mGameId);
        final ContentResolver resolver = context.getContentResolver();

        final String where = GoalTable.Columns.GAME_ID + "=?";
        final String[] whereArgs = new String[] { String.valueOf(mGameId) };
        resolver.delete(ScoresContentProvider.Uris.GOALS, where, whereArgs);

        final ContentValues[] goalValues = DataUtils.getContentValues(response, values);
        resolver.bulkInsert(ScoresContentProvider.Uris.GOALS, goalValues);
    }

}
