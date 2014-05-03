package com.scores.application;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.scores.models.BoxScoreResponse;
import com.scores.models.GameListResponse;
import com.scores.models.GoalListResponse;

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

public class ScoresApi {

	private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm").withZone(DateTimeZone.UTC);

    private static final class Endpoints {
		private static final String BASE_URL = "http://api.thescore.com/nhl/";
		
		public static final String EVENTS(final String start, final String end) {
			return BASE_URL + "events?game_date.in=" + start + "," + end;
		}

        public static final String BOXSCORE(final long id) {
            return BASE_URL + "box_scores/" + id;
        }

        public static final String GOALS(final long id) {
            return BASE_URL + "box_scores/" + id + "/action_goals?rpp=-1";
        }
	}

	public static GameListResponse getGameList(final DateTime date) throws IOException {
		final String startDate = FORMATTER.print(date);
		final String endDate = FORMATTER.print(date.plusDays(1));
		final HttpGet request = new HttpGet(Endpoints.EVENTS(startDate, endDate));
		return execute(request, new Gson(), GameListResponse.class);
	}

    public static BoxScoreResponse getBoxscore(final long id) throws IOException {
        final HttpGet request = new HttpGet(Endpoints.BOXSCORE(id));
        return execute(request, new Gson(), BoxScoreResponse.class);
    }

    public static GoalListResponse getGoalList(final long id) throws IOException {
        final HttpGet request = new HttpGet(Endpoints.GOALS(id));
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