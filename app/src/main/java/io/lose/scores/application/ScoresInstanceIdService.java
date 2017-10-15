package io.lose.scores.application;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.lose.scores.operations.GetStandingsOperation;
import io.lose.scores.operations.GetTeamsOperation;
import io.lose.scores.operations.PostRegOperation;
import io.lose.scores.utils.Logger;
import io.pivotal.arca.service.OperationService;

public class ScoresInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        final String token = FirebaseInstanceId.getInstance().getToken();

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(final String token) {
        try {
            Logger.v("Refresh token: " + token);

            OperationService.start(this, new PostRegOperation(token));

            OperationService.start(this, new GetTeamsOperation());

            OperationService.start(this, new GetStandingsOperation());

        } catch (final Exception e) {
            Logger.ex(e);
        }
    }
}
