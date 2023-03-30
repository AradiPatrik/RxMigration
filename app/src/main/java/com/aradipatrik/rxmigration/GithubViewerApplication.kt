package com.aradipatrik.rxmigration

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import logcat.AndroidLogcatLogger

@HiltAndroidApp
class GithubViewerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidLogcatLogger.installOnDebuggableApp(this)
    }
}