package io.elapse.scores.application;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import io.elapse.scores.activities.LoginActivity;

public class ScoresAuthenticator extends AbstractAccountAuthenticator {

	public static final String ACCOUNT_NAME = "Scores";
	public static final String ACCOUNT_TYPE = "io.elapse.scores.account";
	public static final String AUTH_TOKEN_TYPE = "io.elapse.scores.token";

	private final Context mContext;

	public ScoresAuthenticator(final Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public Bundle addAccount(final AccountAuthenticatorResponse response, final String accountType, final String authTokenType, final String[] requiredFeatures, final Bundle options) throws NetworkErrorException {
		return newAccountBundle(mContext, response);
	}

	@Override
	public Bundle confirmCredentials(final AccountAuthenticatorResponse response, final Account account, final Bundle options) throws NetworkErrorException {
		return null;
	}

	@Override
	public Bundle editProperties(final AccountAuthenticatorResponse response, final String accountType) {
		return null;
	}

	@Override
	public Bundle getAuthToken(final AccountAuthenticatorResponse response, final Account account, final String authTokenType, final Bundle options) throws NetworkErrorException {
		return newAuthTokenBundle(mContext, response, account, authTokenType, options);
	}

	@Override
	public String getAuthTokenLabel(final String authTokenType) {
		return null;
	}

	@Override
	public Bundle hasFeatures(final AccountAuthenticatorResponse response, final Account account, final String[] features) throws NetworkErrorException {
		return newResultBundle(true);
	}

	@Override
	public Bundle updateCredentials(final AccountAuthenticatorResponse response, final Account account, final String authTokenType, final Bundle options) throws NetworkErrorException {
		return null;
	}
	
	private static Bundle newAccountBundle(final Context context, final AccountAuthenticatorResponse response) {
		final Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		final Bundle bundle = new Bundle();
		bundle.putParcelable(AccountManager.KEY_INTENT, intent);
		return bundle;
	}
	
	private static Bundle newResultBundle(final boolean result) {
		final Bundle bundle = new Bundle();
		bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, result);
		return bundle;
	}
	
	private static Bundle newErrorBundle(final String message) {
		final Bundle bundle = new Bundle();
		bundle.putString(AccountManager.KEY_ERROR_MESSAGE, message);
		return bundle;
	}
	
	private static Bundle newAuthTokenBundle(final Context context, final AccountAuthenticatorResponse response, final Account account, final String authTokenType, final Bundle options) throws NetworkErrorException {
		if (!authTokenType.equals(AUTH_TOKEN_TYPE)) {
			return newErrorBundle("Invalid Auth Token Type");
		}
		
		final AccountManager manager = AccountManager.get(context);
		final String authToken = manager.peekAuthToken(account, authTokenType);

		if (!TextUtils.isEmpty(authToken)) {
			return newAuthTokenBundle(account, authToken);
		} else {
			return newAccountBundle(context, response);
		}
	}

	public static Bundle newAuthTokenBundle(final Account account, final String authToken) {
		final Bundle result = new Bundle();
		result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
		result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
		result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
		return result;
	}
}