package io.elapse.scores.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import io.elapse.scores.application.ScoresApi;
import io.elapse.scores.application.ScoresContentProvider;
import io.elapse.scores.models.Game;
import io.elapse.scores.models.GameListResponse;
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
            if (game.getBoxScoreId() != null) {
                final Long boxScoreId = game.getBoxScoreId();
                addDependency(new GetBoxScoreTask(boxScoreId));
            }
        }

		return list;
	}

	@Override
	public void onExecuteProcessing(final Context context, final GameListResponse response) throws Exception {
		final ContentResolver resolver = context.getContentResolver();

        final ContentValues[] gameValues = DataUtils.getContentValues(response);
        resolver.bulkInsert(ScoresContentProvider.Uris.GAMES, gameValues);

        final ContentValues[] boxScoreValues = DataUtils.getContentValues(response.getBoxScores());
        resolver.bulkInsert(ScoresContentProvider.Uris.BOX_SCORES, boxScoreValues);

        final ContentValues[] teamValues = DataUtils.getContentValues(response.getTeams());
		resolver.bulkInsert(ScoresContentProvider.Uris.TEAMS, teamValues);
	}
}