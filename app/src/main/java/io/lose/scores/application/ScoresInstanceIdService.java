package io.lose.scores.application;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.lose.scores.utils.Logger;

public class ScoresInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        final String token = FirebaseInstanceId.getInstance().getToken();

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(final String token) {
        try {
            Logger.v("Refreshed token: " + token);

            LoseApi.postRegistration(token);
        } catch (final Exception e) {
            Logger.v("sendRegistrationToServer failed");
            Logger.ex(e);
        }
    }
}
