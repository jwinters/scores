package io.lose.scores.helpers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.UUID;

import io.lose.scores.application.ScoresAuthenticatorService;
import io.lose.scores.application.ScoresContentProvider;

public class AccountHelper {

    private static final int SYNCABLE = 1;
    private static final int ONE_DAY = 60 * 60 * 24;

    private static final String AUTHORITY = ScoresContentProvider.AUTHORITY;
    private static final String TOKEN_TYPE = ScoresAuthenticatorService.TOKEN_TYPE;
    private static final String ACCOUNT_TYPE = ScoresAuthenticatorService.ACCOUNT_TYPE;


    public static boolean hasAccount(final Context context) {
        return getAccounts(context).length > 0;
    }

    public static Account getAccount(final Context context) {
        final Account[] accounts = getAccounts(context);
        return accounts.length > 0 ? accounts[0] : null;
    }

    public static Account[] getAccounts(final Context context) {
        final AccountManager manager = AccountManager.get(context);
        return manager.getAccountsByType(ACCOUNT_TYPE);
    }

    public static Account addAccount(final Context context, final String name) {
        if (!hasAccount(context)) {
            final Account account = new Account(name, ACCOUNT_TYPE);
            return setupAccount(context, setupSync(account));
        } else {
            return getAccount(context);
        }
    }

    private static Account setupSync(final Account account) {
        ContentResolver.setIsSyncable(account, AUTHORITY, SYNCABLE);
        ContentResolver.setSyncAutomatically(account, AUTHORITY, true);
        ContentResolver.addPeriodicSync(account, AUTHORITY, Bundle.EMPTY, ONE_DAY);
        return account;
    }

    private static Account setupAccount(final Context context, final Account account) {
        final String refreshToken = UUID.randomUUID().toString();
        final String accessToken = UUID.randomUUID().toString();

        final AccountManager manager = AccountManager.get(context);
        manager.addAccountExplicitly(account, null, null);
        manager.setAuthToken(account, TOKEN_TYPE, accessToken);
        manager.setPassword(account, refreshToken);
        return account;
    }

    public static void sync(final Context context) {
        final Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

        final Account account = getAccount(context);
        ContentResolver.requestSync(account, AUTHORITY, bundle);
    }

    public static String refreshAccessToken(final Context context, final Account account) {
        final String refreshToken = getRefreshToken(context, account);
        final String accessToken = UUID.randomUUID().toString();

        final AccountManager manager = AccountManager.get(context);
        manager.setAuthToken(account, TOKEN_TYPE, accessToken);
        manager.setPassword(account, refreshToken);
        return accessToken;
    }

    public static String getAccessToken(final Context context, final Account account) {
        final AccountManager manager = AccountManager.get(context);
        return manager.peekAuthToken(account, TOKEN_TYPE);
    }

    public static String getRefreshToken(final Context context, final Account account) {
        final AccountManager manager = AccountManager.get(context);
        return manager.getPassword(account);
    }

    public static boolean isExpired(final String accessToken) {
        return !TextUtils.isEmpty(accessToken);
    }
}
