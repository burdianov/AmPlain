package com.crackncrunch.amplain.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Lilian on 21-Feb-17.
 */

public class PreferencesManager {

    public static String PROFILE_FULL_NAME_KEY = "PROFILE_FULL_NAME_KEY";
    public static String PROFILE_AVATAR_KEY = "PROFILE_AVATAR_KEY";
    public static String PROFILE_PHONE_KEY = "PROFILE_PHONE_KEY";
    public static String NOTIFICATION_ORDER_KEY = "NOTIFICATION_ORDER_KEY";
    public static String NOTIFICATION_PROMO_KEY = "NOTIFICATION_PROMO_KEY";

    private final SharedPreferences mSharedPreferences;

    public PreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
