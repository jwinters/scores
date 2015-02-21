package io.elapse.scores.application;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import io.elapse.scores.models.ArticleListResponse;
import io.elapse.scores.models.BoxScoreResponse;
import io.elapse.scores.models.GameListResponse;
import io.elapse.scores.models.GoalListResponse;

public class ScoresApi {

    private static final DateTimeFormatter FORMATTER;

    static {
        FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm").withZone(DateTimeZone.UTC);
    }

    private static final class Endpoints {
		private static final String BASE_URL = "http://api.thescore.com/nhl/";
        private static final String NEWS_URL = "http://cms.thescore.com/api/v1/rivers/nhl/news";
	}

    public static String ARTICLES(final String id) {
        if (id != null) {
            return Endpoints.NEWS_URL + "?infinite_scroll_id=" + id;
        } else {
            return Endpoints.NEWS_URL;
        }
    }

    public static String EVENTS(final String start, final String end) {
        return Endpoints.BASE_URL + "events?game_date.in=" + start + "," + end;
    }

    public static String BOX_SCORE(final long id) {
        return Endpoints.BASE_URL + "box_scores/" + id;
    }

    public static String GOALS(final long id) {
        return Endpoints.BASE_URL + "box_scores/" + id + "/action_goals?rpp=-1";
    }

    public static ArticleListResponse getArticleList(final String id) throws IOException {
        final HttpGet request = new HttpGet(ARTICLES(id));
        return execute(request, new Gson(), ArticleListResponse.class);
    }

	public static GameListResponse getGameList(final DateTime date) throws IOException {
		final String startDate = FORMATTER.print(date);
		final String endDate = FORMATTER.print(date.plusDays(1));
		final HttpGet request = new HttpGet(EVENTS(startDate, endDate));
		return execute(request, new Gson(), GameListResponse.class);
	}

    public static BoxScoreResponse getBoxScore(final long id) throws IOException {
        final HttpGet request = new HttpGet(BOX_SCORE(id));
        return execute(request, new Gson(), BoxScoreResponse.class);
    }

    public static GoalListResponse getGoalList(final long id) throws IOException {
        final HttpGet request = new HttpGet(GOALS(id));
        return execute(request, new Gson(), GoalListResponse.class);
    }

	private static <T> T execute(final HttpUriRequest request, final Gson gson, final Type type) throws IOException {
		Log.v("Network", "Request : " + request.getURI());
		final HttpClient client = new DefaultHttpClient();
		final HttpResponse response = client.execute(request);
		Log.v("Network", "Response : " + response.getStatusLine());
		final InputStream inputStream = response.getEntity().getContent();
		final InputStreamReader inputReader = new InputStreamReader(inputStream);
		final JsonReader jsonReader = new JsonReader(inputReader);
		final T list = gson.fromJson(jsonReader, type);
		jsonReader.close();
		return list;
	}
}