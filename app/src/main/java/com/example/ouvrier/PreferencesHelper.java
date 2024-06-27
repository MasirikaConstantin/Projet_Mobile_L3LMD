package com.example.ouvrier;


import android.content.Context;
        import android.content.SharedPreferences;

public class PreferencesHelper {

    private static final String PREF_LANG_KEY = "language";

    private SharedPreferences sharedPreferences;

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    public void setLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_LANG_KEY, language);
        editor.apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(PREF_LANG_KEY, "fr"); // Par défaut, français
    }
}

