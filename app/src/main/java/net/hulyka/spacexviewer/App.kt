package net.hulyka.spacexviewer

import android.os.StrictMode
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import net.hulyka.spacexviewer.BuildConfig.DEBUG
import net.hulyka.spacexviewer.dagger.DaggerAppComponent
import net.hulyka.spacexviewer.timber.CrashlyticsTree
import timber.log.Timber

class App : DaggerApplication() {


    override fun onCreate() {
//        setupStrictMode()
        super.onCreate()
        AndroidThreeTen.init(this)
        setupTimber()
    }

    private fun setupStrictMode() {
        if (DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    private fun setupTimber() {
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

}
