package io.lose.scores.application;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.lose.scores.datasets.ArticleTable;
import io.lose.scores.datasets.BoxScoreTable;
import io.lose.scores.datasets.EventTable;
import io.lose.scores.datasets.GoalTable;
import io.lose.scores.datasets.StandingTable;
import io.lose.scores.datasets.TeamTable;
import io.lose.scores.utils.DataUtils;
import io.lose.scores.utils.Logger;

public class ScoresMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage message) {
        super.onMessageReceived(message);

        final Map<String, String> data = message.getData();

        if (data.containsKey(ArticleTable.Columns.TITLE)) {
            handleArticleMessage(data);

        } else if (data.containsKey(TeamTable.Columns.ABBREVIATION)) {
            handleTeamMessage(data);

        }  else if (data.containsKey(StandingTable.Columns.SHORT_RECORD)) {
            handleStandingMessage(data);

        } else if (data.containsKey(EventTable.Columns.EVENT_STATUS)) {
            handleEventMessage(data);

        } else if (data.containsKey(BoxScoreTable.Columns.PROGRESS)) {
            handleBoxScoreMessage(data);

        } else if (data.containsKey(GoalTable.Columns.PLAYER_NAME)) {
            handleGoalMessage(data);
        }

    }

    private void handleArticleMessage(final Map<String, String> data) {
        final ContentValues values = DataUtils.get(ArticleTable.class, data);
        insertValues(values, ScoresContentProvider.Uris.ARTICLES);
        Logger.v("Article: " + values);
    }

    private void handleTeamMessage(final Map<String, String> data) {
        final ContentValues values = DataUtils.get(TeamTable.class, data);
        insertValues(values, ScoresContentProvider.Uris.TEAMS);
        Logger.v("TEAM: " + values);
    }

    private void handleStandingMessage(final Map<String, String> data) {
        final ContentValues values = DataUtils.get(StandingTable.class, data);
        insertValues(values, ScoresContentProvider.Uris.STANDINGS);
        Logger.v("STANDING: " + values);
    }

    private void handleEventMessage(final Map<String, String> data) {
        final ContentValues values = DataUtils.get(EventTable.class, data);
        insertValues(values, ScoresContentProvider.Uris.EVENTS);
        Logger.v("EVENT: " + values);
    }

    private void handleBoxScoreMessage(final Map<String, String> data) {
        final ContentValues values = DataUtils.get(BoxScoreTable.class, data);
        insertValues(values, ScoresContentProvider.Uris.BOX_SCORES);
        Logger.v("BOX_SCORE: " + values);
    }

    private void handleGoalMessage(final Map<String, String> data) {
        final ContentValues values = DataUtils.get(GoalTable.class, data);
        insertValues(values, ScoresContentProvider.Uris.GOALS);
        Logger.v("GOAL: " + values);
    }

    private void insertValues(final ContentValues values, final Uri uri) {
        final ContentResolver resolver = getContentResolver();
        resolver.insert(uri, values);
        resolver.notifyChange(uri, null);
        resolver.notifyChange(ScoresContentProvider.Uris.SCORES, null);
    }
}