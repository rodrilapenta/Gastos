package ar.com.rodrilapenta.gastos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by arielverdugo on 5/7/17.
 */

public class SessionManager {

    private static SharedPreferences prefs;
    private static SessionManager instance;
    private static final String PREFS_NAME = "_SESSION_", EMAIL = "_EMAIL_";

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void guardarEmail(String email) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public boolean estaLogueado() {
        return !prefs.getString(EMAIL, "").isEmpty();
    }

    public String getEmail() {
        return prefs.getString(EMAIL, "");
    }

    public void logout(Context context) {
        SharedPreferences.Editor editor = prefs.edit();

        editor.remove(EMAIL);

        editor.apply();

        ((Activity) context).finish();
    }
}