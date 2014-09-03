package com.tale.basethings;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.tale.basethings.util.Timber;

/**
 * Created by TALE on 9/3/2014.
 */
public class DemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.DebugTree debugTree = new Timber.DebugTree();
            debugTree.tag("DemoApp");
            Timber.plant(debugTree);
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Timber.d(activity + "{onActivityCreated}");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Timber.d(activity + "{onActivityStarted}");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Timber.d(activity + "{onActivityResumed}");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Timber.d(activity + "{onActivityPaused}");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Timber.d(activity + "{onActivityStopped}");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Timber.d(activity + "{onActivitySaveInstanceState}");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Timber.d(activity + "{onActivityDestroyed}");
            }
        });
    }
}
