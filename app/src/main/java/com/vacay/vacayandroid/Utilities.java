package com.vacay.vacayandroid;

import android.content.Context;
import android.widget.Toast;

public class Utilities {
    private static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
