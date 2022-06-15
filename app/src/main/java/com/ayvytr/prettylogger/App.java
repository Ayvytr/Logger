package com.ayvytr.prettylogger;

import android.app.Application;

import com.ayvytr.logger.L;

/**
 * @author Administrator
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L.settings().showThreadInfo(true);
    }
}
