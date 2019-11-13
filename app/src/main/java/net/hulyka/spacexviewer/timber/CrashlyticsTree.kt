package net.hulyka.spacexviewer.timber

import android.util.Log.INFO
import com.crashlytics.android.Crashlytics
import timber.log.Timber

class CrashlyticsTree : Timber.Tree() {

    override fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable?
    ) {
        if (priority > INFO) {
            Crashlytics.log(message)
            t?.let { Crashlytics.logException(it) }
        }
    }

}