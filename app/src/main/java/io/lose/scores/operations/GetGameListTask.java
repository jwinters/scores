package io.lose.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import io.lose.scores.application.ScoresApi;
import io.lose.scores.application.ScoresContentProvider;
import io.lose.scores.models.Game;
import io.lose.scores.models.GameListResponse;
import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.service.Task;
import io.pivotal.arca.threading.Identifier;

public class GetGameListTask extends Task<GameListResponse> {

	private final String mDate;

	public GetGameListTask(final String date) {
		mDate = date;
	}

	@Override
	public Identifier<?> onCreateIdentifier() {
		return new Identifier<String>("get_game_list:" + mDate);
	}
	
	@Override
	public GameListResponse onExecuteNetworking(final Context context) throws Exception {
		final DateTime time = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(mDate);
        final GameListResponse list = ScoresApi.getGameList(time);

        for (final Game game : list) {
            final Long boxScoreId = game.getBoxScoreId();

            if (boxScoreId != null) {
                addDependency(new GetBoxScoreTask(boxScoreId));
                addDependency(new GetGoalListTask(boxScoreId));
            }
        }

		return list;
	}

	@Override
	public void onExecuteProcessing(final Context context, final GameListResponse response) throws Exception {
		final ContentResolver resolver = context.getContentResolver();

        final ContentValues[] gameValues = DataUtils.getContentValues(response);
        resolver.bulkInsert(ScoresContentProvider.Uris.EVENTS, gameValues);

        final ContentValues[] boxScoreValues = DataUtils.getContentValues(response.getBoxScores());
        resolver.bulkInsert(ScoresContentProvider.Uris.BOX_SCORES, boxScoreValues);

        final ContentValues[] teamValues = DataUtils.getContentValues(response.getTeams());
		resolver.bulkInsert(ScoresContentProvider.Uris.TEAMS, teamValues);
	}
}