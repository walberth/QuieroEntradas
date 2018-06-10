package com.cloudvision.utp.quieroentradas.helper.ui;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by Gustavo Ramos M on 5/06/2018.
 */
public class Splash extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(1200);
    }
}
