package com.group.github

import android.app.Application
import com.flurry.android.FlurryAgent
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var self: Application
        fun applicationContext(): Application {
            return self
        }
    }

    init {
        self = this
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "GPX2XZSFZWYTQD4XKCCP")
        }
    }
}