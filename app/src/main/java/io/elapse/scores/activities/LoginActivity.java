package io.elapse.scores.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.elapse.scores.R;
import io.elapse.scores.application.ScoresAuthenticator;
import io.elapse.scores.application.ScoresContentProvider;

public class LoginActivity extends Activity {
	
	private static final int ONE_DAY = 60 * 60 * 24;
	
	public static final void newInstance(final Context context) {
		final Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		if (getAccounts().length > 0) {
			finish();
		}
	}
	
	private Account[] getAccounts() {
		final String type = ScoresAuthenticator.ACCOUNT_TYPE;
		final AccountManager manager = AccountManager.get(this);
		final Account[] accounts = manager.getAccountsByType(type);
		return accounts;
	}
	
	public void onAddAccountClicked(final View view) {
		setupAccount(this);
		finish();
	}
	
	private static void setupAccount(final Context context) {
		final String name = ScoresAuthenticator.ACCOUNT_NAME;
		final String type = ScoresAuthenticator.ACCOUNT_TYPE;
		final String authority = ScoresContentProvider.AUTHORITY;
		final Account account = new Account(name, type);
		
		ContentResolver.setIsSyncable(account, authority, 1);
		ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, ONE_DAY);
		
		final AccountManager manager = AccountManager.get(context);
		manager.addAccountExplicitly(account, null, null);
		manager.setAuthToken(account, type, "");
	}
}