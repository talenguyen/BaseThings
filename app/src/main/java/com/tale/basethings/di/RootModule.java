/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tale.basethings.di;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;

import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.tale.basethings.DemoApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module created to work as junction of every module with an application scope.
 * <p/>
 * This module provides every application scope dependencies related with the AndroidSDK.
 *
 * @author Pedro Vicente Gómez Sánchez
 */

@Module(
        includes = {
//        ExecutorModule.class, TvShowsModule.class,
        },
        injects = {
            DemoApp.class
        }, library = true)
public final class RootModule {

    private final Application application;

    public RootModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(application);
    }

    @Provides
    JobManager provideJobManager() {
        return ((DemoApp) application).getJobManager();
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }
}
