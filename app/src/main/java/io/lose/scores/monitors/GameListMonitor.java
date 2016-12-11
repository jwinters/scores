package io.lose.scores.monitors;

import android.content.Context;

import io.lose.scores.operations.GetEventsOperation;
import io.pivotal.arca.dispatcher.Query;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.monitor.RequestMonitor.AbstractRequestMonitor;
import io.pivotal.arca.service.Operation;
import io.pivotal.arca.service.OperationService;
import io.pivotal.arca.utils.ArrayUtils;

public class GameListMonitor extends AbstractRequestMonitor {

	@Override
	public int onPostExecute(final Context context, final Query request, final QueryResult result) {

        final boolean shouldSync = result.getData().getCount() == 0;

        if (shouldSync) {
            final String[] args = request.getWhereArgs();

            if (ArrayUtils.isNotEmpty(args)) {
                final Operation operation = new GetEventsOperation(args[0]);
                return OperationService.start(context, operation) ? Flags.DATA_SYNCING : 0;
            }
        }

        return Flags.DATA_VALID;
    }
}