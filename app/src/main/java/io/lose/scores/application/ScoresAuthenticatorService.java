package io.lose.scores.application;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import io.lose.scores.activities.AccountActivity;
import io.lose.scores.helpers.AccountHelper;

public class ScoresAuthenticatorService extends Service {

	public static final String ACCOUNT_TYPE = "io.lose.scores.account";
	public static final String TOKEN_TYPE = "io.lose.scores.token";

	private ScoresAuthenticator mAuthenticator = new ScoresAuthenticator(this);

	@Override
	public IBinder onBind(final Intent intent) {
		return mAuthenticator.getIBinder();
	}

	public static class ScoresAuthenticator extends AbstractAccountAuthenticator {

		private final Context mContext;

		private ScoresAuthenticator(final Context context) {
			super(context);
			mContext = context;
		}

		@Override
		public Bundle addAccount(final AccountAuthenticatorResponse response, final String accountType, final String authTokenType, final String[] requiredFeatures, final Bundle options) throws NetworkErrorException {
			return newAccountBundle(response);
		}

		@Override
		public Bundle confirmCredentials(final AccountAuthenticatorResponse response, final Account account, final Bundle options) throws NetworkErrorException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Bundle editProperties(final AccountAuthenticatorResponse response, final String accountType) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Bundle getAuthToken(final AccountAuthenticatorResponse response, final Account account, final String authTokenType, final Bundle options) throws NetworkErrorException {
			return newAuthTokenBundle(response, account);
		}

		@Override
		public String getAuthTokenLabel(final String authTokenType) {
			return authTokenType;
		}

		@Override
		public Bundle hasFeatures(final AccountAuthenticatorResponse response, final Account account, final String[] features) throws NetworkErrorException {
			return newResultBundle(false);
		}

		@Override
		public Bundle updateCredentials(final AccountAuthenticatorResponse response, final Account account, final String authTokenType, final Bundle options) throws NetworkErrorException {
			throw new UnsupportedOperationException();
		}


		private Bundle newAccountBundle(final AccountAuthenticatorResponse response) {
			final Bundle bundle = new Bundle();
			bundle.putParcelable(AccountManager.KEY_INTENT, newLoginIntent(response));
			return bundle;
		}

		private Intent newLoginIntent(final AccountAuthenticatorResponse response) {
			final Intent intent = new Intent(mContext, AccountActivity.class);
			intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
			return intent;
		}

		private Bundle newAuthTokenBundle(final Account account, final String authToken) {
			final Bundle bundle = new Bundle();
			bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
			bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
			bundle.putString(AccountManager.KEY_AUTHTOKEN, authToken);
			return bundle;
		}

		private Bundle newResultBundle(final boolean result) {
			final Bundle bundle = new Bundle();
			bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, result);
			return bundle;
		}

		private Bundle newErrorBundle(final Account account, final Throwable throwable) {
			final String message = throwable != null ? throwable.toString() : null;
			final Bundle bundle = new Bundle();
			bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
			bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
			bundle.putString(AccountManager.KEY_ERROR_MESSAGE, message);
			bundle.putInt(AccountManager.KEY_ERROR_CODE, 1);
			return bundle;
		}

		private Bundle newAuthTokenBundle(final AccountAuthenticatorResponse response, final Account account) {

			final String accessToken = AccountHelper.getAccessToken(mContext, account);

			if (!AccountHelper.isExpired(accessToken)) {
				return newAuthTokenBundle(account, accessToken);

			} else {
				return newRefreshTokenBundle(response, account);
			}
		}

		private Bundle newRefreshTokenBundle(final AccountAuthenticatorResponse response, final Account account) {
			try {
				final String accessToken = AccountHelper.refreshAccessToken(mContext, account);

				return newAuthTokenBundle(account, accessToken);

			} catch (final NotAuthorizedException e) {
				return newAccountBundle(response);

			} catch (final Exception e) {
				return newErrorBundle(account, e.getCause());
			}
		}
	}

	public static class NotAuthorizedException extends RuntimeException {}
}