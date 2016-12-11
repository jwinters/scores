package io.lose.scores.monitors;

import android.content.Context;

import io.lose.scores.operations.GetGoalsOperation;
import io.pivotal.arca.dispatcher.Query;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.monitor.RequestMonitor.AbstractRequestMonitor;
import io.pivotal.arca.service.Operation;
import io.pivotal.arca.service.OperationService;

public class GoalListMonitor extends AbstractRequestMonitor {

    private boolean mSynced = false;

    @Override
    public int onPostExecute(final Context context, final Query request, final QueryResult result) {

        if (!mSynced) {
            final Operation operation = new GetGoalsOperation(request.getWhereArgs()[0]);
            return (mSynced = OperationService.start(context, operation)) ? Flags.DATA_SYNCING : Flags.DATA_VALID;
        }

        return Flags.DATA_VALID;
    }
}