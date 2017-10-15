package io.lose.scores.operations;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;

import io.lose.scores.application.ScoresApi;
import io.lose.scores.application.ScoresContentProvider;
import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.TaskOperation;

public class PostRegOperation extends TaskOperation<ContentValues> {

    private final String mRegId;

    public PostRegOperation(final String regId) {
        super(ScoresContentProvider.BASE_URI);
        mRegId = regId;
    }

    private PostRegOperation(final Parcel in) {
        super(in);
        mRegId = in.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mRegId);
    }

    @Override
    public ContentValues onExecute(final Context context) throws Exception {
        return DataUtils.getContentValues(ScoresApi.postRegistration(mRegId));
    }

    @Override
    public void onComplete(final Context context, final Results results) {
        if (results.hasFailedTasks()) {
            final ServiceError error = results.getFailedTasks().get(0).getError();
            ErrorBroadcaster.broadcast(context, getUri(), error.getCode(), error.getMessage());
        }
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public PostRegOperation createFromParcel(final Parcel in) {
            return new PostRegOperation(in);
        }

        @Override
        public PostRegOperation[] newArray(final int size) {
            return new PostRegOperation[size];
        }
    };
}
