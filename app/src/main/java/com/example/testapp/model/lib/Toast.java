package com.example.testapp.model.lib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class Toast extends android.widget.Toast {
    private Context context;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public Toast(Context context) {
        super(context);
        this.context = context;
    }

    public static void makeLong(Context context, String text) {
        android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_LONG).show();
    }

    public static void makeShort(Context context, String text) {
        android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT).show();
    }

    public void makeLong(String text) {
        android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_LONG).show();
    }

    public void makeShort(String text) {
        android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT).show();
    }
}
