package com.andruid.magic.newsdaily.worker

import android.content.Context
import androidx.work.*
import com.andruid.magic.newsdaily.util.logi
import java.util.concurrent.TimeUnit

object WorkerScheduler {
    fun scheduleNewsWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = PeriodicWorkRequestBuilder<NewsWorker>(1L, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        logi("scheduling news worker now")

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork("news-worker", ExistingPeriodicWorkPolicy.REPLACE, request)
    }
}