package io.lose.scores.monitors;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;

import io.lose.scores.operations.GetEventsOperation;
import io.pivotal.arca.dispatcher.Query;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.monitor.RequestMonitor.AbstractRequestMonitor;
import io.pivotal.arca.service.Operation;
import io.pivotal.arca.service.OperationService;

public class GameListMonitor extends AbstractRequestMonitor {

    private Set<String> mSynced = new HashSet<>();

    @Override
    public int onPostExecute(final Context context, final Query request, final QueryResult result) {

        final String date = request.getWhereArgs()[0];

        if (!mSynced.contains(date)) {
            mSynced.add(date);

            final Operation operation = new GetEventsOperation(date);
            return OperationService.start(context, operation) ? Flags.DATA_SYNCING : Flags.DATA_VALID;
        }

        return Flags.DATA_VALID;
    }
}