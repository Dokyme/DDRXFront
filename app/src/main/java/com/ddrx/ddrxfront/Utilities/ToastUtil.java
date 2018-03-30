package com.ddrx.ddrxfront.Utilities;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by dokym on 2018/3/24.
 */

public class ToastUtil {
    public static void prompt(Context context, String text, boolean isInCallback) {
        if (isInCallback)
            Looper.prepare();
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        if (isInCallback)
            Looper.loop();
    }

    public static void prompt(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
