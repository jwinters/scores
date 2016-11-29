package io.lose.scores.activities;

import android.accounts.Account;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.lose.scores.R;
import io.lose.scores.helpers.AccountHelper;


public class AccountActivity extends AppCompatActivity {

    private static final int IF_NOT_EXISTS = 1;

    private interface Extras {
        String FINISH_IF_EXISTS = "finish_if_exists";
    }

    public static void newInstance(final Activity activity) {
        final Intent intent = new Intent(activity, AccountActivity.class);
        activity.startActivity(intent);
    }

    public static void newInstanceIfNotExists(final Activity activity) {
        final Intent intent = new Intent(activity, AccountActivity.class);
        intent.putExtra(Extras.FINISH_IF_EXISTS, true);
        activity.startActivityForResult(intent, IF_NOT_EXISTS);
    }

    @Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        final boolean finish = getIntent().getBooleanExtra(Extras.FINISH_IF_EXISTS, false);

        getAccountFragment().finishIfExists(finish);
	}

    private AccountFragment getAccountFragment() {
        final FragmentManager manager = getFragmentManager();
        return (AccountFragment) manager.findFragmentById(R.id.fragment_account);
    }

    public static class AccountFragment extends Fragment implements View.OnClickListener {

        private TextView mTextView;
        private Button mButton;

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_account, container, false);
        }

        @Override
        public void onViewCreated(final View view, final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mTextView = (TextView) view.findViewById(R.id.account_name);

            mButton = (Button) view.findViewById(R.id.account_button);
            mButton.setOnClickListener(this);

            addAccount("scores");
        }

        @Override
        public void onClick(final View view) {
            if (view == mButton) {
                addAccount(mTextView.getText().toString());
            }
        }

        private void addAccount(final String username) {
            if (!TextUtils.isEmpty(username)) {
                final Activity activity = getActivity();
                AccountHelper.addAccount(activity, username);
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        }

        public void finishIfExists(final boolean finish) {
            final Activity activity = getActivity();
            final Account account = AccountHelper.getAccount(activity);

            if (account != null) {
                if (finish) {
                    activity.setResult(Activity.RESULT_OK);
                    activity.finish();
                } else {
                    mTextView.setText(account.name);
                }
            }
        }
    }
}