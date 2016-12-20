package io.lose.scores.application;

import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.HTTP;
import retrofit.http.Headers;
import retrofit.http.Query;

public class LoseApi {

    interface Service {
        String SERVER_URL = "http://api.lose.io";
        String SERVER_TOKEN = "bearer V2VkIDI4IFNlcCAyMDE2IDIwOjEzOjIxIEVEVAo=";

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "POST", path = "/v1/registrations", hasBody = true)
        Call<Registration> postRegistration(@Body Registration reg);

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "GET", path = "/v1/registrations")
        Call<List<Registration>> getRegistrations(@Query("reg_id") String regId);

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "DELETE", path = "/v1/registrations")
        Call<Registration> deleteRegistration(@Query("reg_id") String regId);

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "GET", path = "/v1/scores/articles")
        Call<List<Map<String, String>>> fetchArticles();

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "GET", path = "/v1/scores/teams")
        Call<List<Map<String, String>>> fetchTeams();

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "GET", path = "/v1/scores/standings")
        Call<List<Map<String, String>>> fetchStandings();

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "GET", path = "/v1/scores/events")
        Call<List<Map<String, String>>> fetchEvents(@Query("start_date") String start, @Query("end_date") String end);

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "GET", path = "/v1/scores/box_score")
        Call<Map<String, String>> fetchBoxScore(@Query("id") String id);

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "GET", path = "/v1/scores/goals")
        Call<List<Map<String, String>>> fetchGoals(@Query("id") String id);
    }

    private static final OkHttpClient CLIENT = new OkHttpClient();

    static {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        CLIENT.interceptors().add(logging);
    }

    private static final Service BASE_SERVICE = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Service.SERVER_URL).client(CLIENT)
            .build().create(Service.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormat
            .forPattern("yyyy-MM-dd'T'HH:mm")
            .withZone(DateTimeZone.UTC);


    public static Registration postRegistration(final String regId) throws Exception {
        final Registration registration = new Registration(regId);
        return BASE_SERVICE.postRegistration(registration).execute().body();
    }

    public static List<Registration> getRegistration(final String regId) throws Exception {
        return BASE_SERVICE.getRegistrations(regId).execute().body();
    }

    public static Registration deleteRegistration(final String regId) throws Exception {
        return BASE_SERVICE.deleteRegistration(regId).execute().body();
    }

    public static List<Map<String, String>> getArticles() throws Exception {
        return BASE_SERVICE.fetchArticles().execute().body();
    }

    public static List<Map<String, String>> getTeams() throws Exception {
        return BASE_SERVICE.fetchTeams().execute().body();
    }

    public static List<Map<String, String>> getStandings() throws Exception {
        return BASE_SERVICE.fetchStandings().execute().body();
    }

    public static List<Map<String, String>> getEvents(final DateTime date) throws Exception {
        final String start = FORMATTER.print(date);
        final String end = FORMATTER.print(date.plusDays(1));
        return BASE_SERVICE.fetchEvents(start, end).execute().body();
    }

    public static Map<String, String> getBoxScore(final String id) throws Exception {
        return BASE_SERVICE.fetchBoxScore(id).execute().body();
    }

    public static List<Map<String, String>> getGoals(final String id) throws Exception {
        return BASE_SERVICE.fetchGoals(id).execute().body();
    }

    private static class Registration {

        private interface Fields {
            String REG_ID = "reg_id";
        }

        @SerializedName(Fields.REG_ID)
        private String mRegId;

        private Registration(final String regId) {
            mRegId = regId;
        }
    }
}