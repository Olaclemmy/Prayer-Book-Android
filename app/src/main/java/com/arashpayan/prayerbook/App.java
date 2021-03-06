package com.arashpayan.prayerbook;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.arashpayan.util.L;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidx.appcompat.app.AppCompatDelegate;

public class App extends Application {

    private Handler mMainThreadHandler;
    private Handler mBackgroundHandler;

    private static volatile App app;
    private static final int LatestDatabaseVersion = 17;

    @Override
    public void onCreate() {
        app = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate();

        Prefs.init(this);
        copyDatabaseFile();
        mMainThreadHandler = new Handler(Looper.getMainLooper());

        HandlerThread bgThread = new HandlerThread("Prayer Book Background");
        bgThread.start();
        mBackgroundHandler = new Handler(bgThread.getLooper());
    }

    private void copyDatabaseFile() {
        int dbVersion = Prefs.get().getDatabaseVersion();
        File databaseFile = new File(getFilesDir(), "pbdb.db");
        PrayersDB.databaseFile = databaseFile;
        if (dbVersion != LatestDatabaseVersion) {
            // then we need to copy over the latest database
            L.i("database file: " + databaseFile.getAbsolutePath());
            try {
                BufferedInputStream is = new BufferedInputStream(getAssets().open("pbdb.jet"), 8192);
                OutputStream os = new BufferedOutputStream(new FileOutputStream(databaseFile), 8192);
                byte[] data = new byte[4096];
                while (is.available() != 0) {
                    int numRead = is.read(data);
                    if (numRead != 0)
                        os.write(data);
                }
                is.close();
                os.close();
                Prefs.get().setDatabaseVersion(LatestDatabaseVersion);
            } catch (IOException ex) {
                L.w("Error writing prayer database", ex);
            }
        }
    }

    public static void runOnUiThread(Runnable r) {
        app.mMainThreadHandler.post(r);
    }

    public static void runInBackground(Runnable r) {
        app.mBackgroundHandler.post(r);
    }

}
