package com.madememagic.limelight.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkManagerHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun scheduleLabelReminders() {
        AppWorkerManager.scheduleImmediate(context)
    }

    fun schedulePeriodicWork() {
        AppWorkerManager.schedule(context)
    }
}
