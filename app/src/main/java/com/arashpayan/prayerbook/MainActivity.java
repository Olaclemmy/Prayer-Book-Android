package com.arashpayan.prayerbook;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.arashpayan.util.L;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        BottomNavigationView bar = findViewById(R.id.bottom_bar);
        bar.setOnNavigationItemSelectedListener(barItemListener);
        bar.setOnNavigationItemReselectedListener(reselectListener);

        String appName = getString(R.string.app_name);
        if (Build.VERSION.SDK_INT > 27) {
            setTaskDescription(new ActivityManager.TaskDescription(appName, R.mipmap.ic_launcher, R.color.task_header));
        } else if (Build.VERSION.SDK_INT > 20) {
            Bitmap appIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            int headerColor = ContextCompat.getColor(this, R.color.task_header);
            setTaskDescription(new ActivityManager.TaskDescription(appName, appIcon, headerColor));
        }

        if (savedInstanceState == null) {
            CategoriesFragment categoriesFragment = new CategoriesFragment();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main_container, categoriesFragment, CategoriesFragment.CATEGORIES_TAG);
            ft.commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener barItemListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.prayers:
                    L.i("PRAYERS");
                    break;
                case R.id.bookmarks:
                    L.i("BOOKMARKS");
                    break;
                case R.id.recents:
                    L.i("RECENTS");
                    break;
                case R.id.languages:
                    L.i("LANGUAGES");
                    break;
                case R.id.about:
                    L.i("ABOUT");
                    break;
            }
            return true;
        }
    };

    private BottomNavigationView.OnNavigationItemReselectedListener reselectListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.prayers:
                    L.i("reselected PRAYERS");
                    break;
                case R.id.bookmarks:
                    L.i("reselected BOOKMARKS");
                    break;
                case R.id.recents:
                    L.i("reselected RECENTS");
                    break;
                case R.id.languages:
                    L.i("reselected LANGUAGES");
                    break;
                case R.id.about:
                    L.i("reselected ABOUT");
                    break;
            }
        }
    };
}