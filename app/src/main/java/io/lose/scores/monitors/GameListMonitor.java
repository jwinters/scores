package io.lose.scores.monitors;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.pivotal.arca.dispatcher.Query;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.monitor.RequestMonitor.AbstractRequestMonitor;

public class GameListMonitor extends AbstractRequestMonitor {

	private static final int DELAY = 5 * 1000;
	
	private Map<String, Long> mLastSync = new HashMap<String, Long>();

	private boolean shouldSync(final QueryResult result, final String date) {
        final Long lastSync = mLastSync.get(date);
        return lastSync == null || System.currentTimeMillis() > (lastSync + DELAY);
	}

	public boolean startDataSync(final Context context, final Query request, final String date) {
//        final GetGameListOperation operation = new GetGameListOperation(request.getUri(), date);
//        return OperationService.start(context, operation);
        return false;
	}

	@Override
	public int onPostExecute(final Context context, final Query request, final QueryResult result) {
        final String[] args= request.getWhereArgs();
        if (args != null && args.length > 0) {
            final String date = args[0];
            if (shouldSync(result, date) && startDataSync(context, request, date)) {
                mLastSync.put(date, System.currentTimeMillis());
                return Flags.DATA_SYNCING;
            }
        }
        return 0;
    }
}