package com.demoapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Patterns;
import android.util.TypedValue;

public class Utils {

    private static ProgressDialog pDialog;

    public static void getProgressDialog(Activity activity, String Message) {
        if ((pDialog == null) || (!pDialog.isShowing())) {
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(Message);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if ((pDialog != null) && (pDialog.isShowing()))
            pDialog.dismiss();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
