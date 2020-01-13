package com.andruid.magic.newsdaily.application

import android.app.Application
import androidx.preference.PreferenceManager
import com.andruid.magic.newsdaily.R
import com.andruid.magic.newsloader.api.NewsRepository
import com.andruid.magic.texttoaudiofile.api.TtsApi
import com.blongho.country_data.World

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NewsRepository.init(this)
        World.init(this)
        TtsApi.init(this)

        PreferenceManager.setDefaultValues(this, R.xml.app_preferences, false)
    }

    override fun onTerminate() {
        super.onTerminate()
        TtsApi.release()
    }
}