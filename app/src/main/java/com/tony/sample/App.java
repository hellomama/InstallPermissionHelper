package com.tony.sample;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by dev on 8/6/18.
 */

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static App sApp;
    private static final Handler sUIHandler = new Handler(Looper.getMainLooper());


    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }

    public static App currentApp()
    {
        return sApp;
    }

    public static Context getContext()
    {
        Context context = sApp.getApplicationContext();
        if (context == null)
        {
            Log.w(TAG, "the context is null");
        }

        return context;
    }

    public static boolean execute(Runnable aRunnable)
    {
        boolean success = false;
        if (aRunnable != null)
        {
            success = sUIHandler.post(aRunnable);

            if (!success)
            {
                Log.w(TAG, "the Runnable was failure placed in to the wsMessage queue");
            }
        }

        return success;
    }
}
