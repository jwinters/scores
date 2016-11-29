package io.lose.scores.application;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

import io.lose.scores.models.ArticleListResponse;
import io.lose.scores.models.BoxScoreResponse;
import io.lose.scores.models.GameListResponse;
import io.lose.scores.models.GoalListResponse;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class ScoresApi {


    interface Service {
        String BASE_URL = "http://api.thescore.com";
        String NEWS_URL = "http://cms.thescore.com";

        @GET("/api/v1/rivers/nhl/news")
        Call<ArticleListResponse> fetchArticles(@Query("infinite_scroll_id") final String id);

        @GET("/nhl/events")
        Call<GameListResponse> fetchGames(@Query("game_date.in") final String date);

        @GET("/nhl/box_scores/{id}")
        Call<BoxScoreResponse> fetchBoxScore(@Path("id") final String id);

        @GET("/nhl/box_scores/{id}/action_goals")
        Call<GoalListResponse> fetchGoals(@Path("id") final String id);
    }


    private static final OkHttpClient CLIENT = new OkHttpClient();

    static {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        CLIENT.interceptors().add(logging);
    }

    private static final Service BASE_SERVICE = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(CLIENT)
            .baseUrl(Service.BASE_URL).build()
            .create(Service.class);

    private static final Service NEWS_SERVICE = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(CLIENT)
            .baseUrl(Service.NEWS_URL).build()
            .create(Service.class);


    private static final DateTimeFormatter FORMATTER = DateTimeFormat
            .forPattern("yyyy-MM-dd'T'HH:mm")
            .withZone(DateTimeZone.UTC);


    public static ArticleListResponse getArticleList(final String id) throws IOException {
        final Call<ArticleListResponse> call = NEWS_SERVICE.fetchArticles(id);
        return call.execute().body();
    }

    public static GameListResponse getGameList(final DateTime date) throws IOException {
        final String dateString = String.format("%s,%s", FORMATTER.print(date), FORMATTER.print(date.plusDays(1)));
        final Call<GameListResponse> call = BASE_SERVICE.fetchGames(dateString);
        return call.execute().body();
	}

    public static BoxScoreResponse getBoxScore(final long id) throws IOException {
        final Call<BoxScoreResponse> call = BASE_SERVICE.fetchBoxScore(String.valueOf(id));
        return call.execute().body();
    }

    public static GoalListResponse getGoalList(final long id) throws IOException {
        final Call<GoalListResponse> call = BASE_SERVICE.fetchGoals(String.valueOf(id));
        return call.execute().body();
    }
}