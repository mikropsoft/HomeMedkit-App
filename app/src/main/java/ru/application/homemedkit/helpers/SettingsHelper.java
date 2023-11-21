package ru.application.homemedkit.helpers;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static androidx.appcompat.app.AppCompatDelegate.setApplicationLocales;
import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import ru.application.homemedkit.R;

public class SettingsHelper {
    public static final String LANGUAGE = "language";
    public static final String KEY_FRAGMENT = "default_start_fragment";
    public static final String APP_THEME = "app_theme";
    private static final String KEY_ORDER = "sorting_order";
    public static String DEFAULT_FRAGMENT;
    public static String DEFAULT_SORTING;
    public static String DEFAULT_THEME;
    private final SharedPreferences preferences;
    private String[] themes;

    public SettingsHelper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        loadValues(context);
    }

    public String getHomePage() {
        return preferences.getString(KEY_FRAGMENT, DEFAULT_FRAGMENT);
    }

    public String getSortingOrder() {
        return preferences.getString(KEY_ORDER, DEFAULT_SORTING);
    }

    public void setSortingOrder(String order) {
        preferences.edit().putString(KEY_ORDER, order).apply();
    }

    public void changeLanguage(String language) {
        setApplicationLocales(LocaleListCompat.forLanguageTags(language));
    }

    public void getAppTheme() {
        String theme = preferences.getString(APP_THEME, DEFAULT_THEME);
        changeTheme(theme);
    }

    private void changeTheme(String theme) {
        if (theme.equals(themes[0])) setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
        else if (theme.equals(themes[1])) setDefaultNightMode(MODE_NIGHT_NO);
        else setDefaultNightMode(MODE_NIGHT_YES);
    }

    public void setAppTheme(String theme) {
        preferences.edit().putString(APP_THEME, theme).apply();
        changeTheme(theme);
    }

    private void loadValues(Context context) {
        String language = Locale.getDefault().getLanguage();
        preferences.edit().putString(LANGUAGE, language).apply();

        String[] pages = context.getResources().getStringArray(R.array.fragment_pages);
        String[] types = context.getResources().getStringArray(R.array.sorting_types);
        themes = context.getResources().getStringArray(R.array.app_themes);

        DEFAULT_FRAGMENT = pages[0];
        DEFAULT_SORTING = types[0];
        DEFAULT_THEME = themes[0];
    }
}