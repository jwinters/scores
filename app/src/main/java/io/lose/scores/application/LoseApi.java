package io.lose.scores.application;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
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
        Call<Registration> postRegistration(@Body final Registration reg);

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "GET", path = "/v1/registrations", hasBody = false)
        Call<List<Registration>> getRegistrations(@Query("reg_id") final String regId);

        @Headers({"Authorization: " + SERVER_TOKEN})
        @HTTP(method = "DELETE", path = "/v1/registrations", hasBody = false)
        Call<Registration> deleteRegistration(@Query("reg_id") final String regId);
    }

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Service.SERVER_URL)
            .build();

    public static Registration postRegistration(final String regId) throws Exception {
        final Registration registration = new Registration(regId);
        final Service service = RETROFIT.create(Service.class);
        final Call<Registration> call = service.postRegistration(registration);
        final Response<Registration> response = call.execute();
        return response.body();
    }

    public static List<Registration> getRegistration(final String regId) throws Exception {
        final Service service = RETROFIT.create(Service.class);
        final Call<List<Registration>> call = service.getRegistrations(regId);
        final Response<List<Registration>> response = call.execute();
        return response.body();
    }

    public static Registration deleteRegistration(final String regId) throws Exception {
        final Service service = RETROFIT.create(Service.class);
        final Call<Registration> call = service.deleteRegistration(regId);
        final Response<Registration> response = call.execute();
        return response.body();
    }

    private static class Registration {

        public interface Fields {
            String REG_ID = "reg_id";
        }

        @SerializedName(Fields.REG_ID)
        private String mRegId;

        public Registration(final String regId) {
            mRegId = regId;
        }
    }
}