package com.madememagic.limelight.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        val movieId = intent.getStringExtra(NotificationManager.EXTRA_MOVIE_ID) ?: return

        when (intent.action) {
            NotificationManager.ACTION_DISMISS -> {
                notificationManager.cancelNotification(movieId)
            }

            else -> {
                // Show the notification
                val title = intent.getStringExtra(NotificationManager.EXTRA_MOVIE_TITLE) ?: ""
                val content = intent.getStringExtra(NotificationManager.EXTRA_MOVIE_CONTENT) ?: ""
                notificationManager.showNotification(movieId, title, content)
            }
        }
    }
}
