package com.cloudvision.utp.quieroentradas.presentation.ui.helpers;

import android.app.Application;
import android.os.SystemClock;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Gustavo Ramos M on 5/06/2018.
 */
public class Splash extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        SystemClock.sleep(1200);
    }
}
