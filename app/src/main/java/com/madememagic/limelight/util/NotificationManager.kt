package com.madememagic.limelight.util

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.madememagic.limelight.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = NotificationManagerCompat.from(context)
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    init {
        createNotificationChannel()
    }


    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Movie Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for moview reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(movieId: String, title: String, content: String) {
        if (!hasNotificationPermission()) {
            return
        }

        try {
            val dismissIntent = Intent(context, NotificationReceiver::class.java).apply {
                action = ACTION_DISMISS
                putExtra(EXTRA_MOVIE_ID, movieId)
            }

            val dismissPendingIntent = PendingIntent.getBroadcast(
                context,
                movieId.hashCode() + 1,
                dismissIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(0, "Dismiss", dismissPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build()

            notificationManager.notify(movieId.hashCode(), notification)
        } catch (e: SecurityException) {
            // Handle permission denied
        }
    }

    fun cancelNotification(movieId: String) {
        // Cancel the notification
        notificationManager.cancel(movieId.hashCode())

        // Cancel any pending alarms
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            movieId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    fun showAuthNotification(isSuccess: Boolean, isNewUser: Boolean) {
        if (!hasNotificationPermission()) {
            return
        }

        try {
            val title = when {
                isSuccess && isNewUser -> "Welcome to LimeLight!"
                isSuccess -> "Welcome Back!"
                else -> "Sign In Failed"
            }

            val content = when {
                isSuccess && isNewUser -> "Thank you for joining LimeLight. Start enjoying movies!"
                isSuccess -> "Successfully signed in to your account"
                else -> "There was a problem signing in. Please try again."
            }

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build()

            notificationManager.notify(AUTH_NOTIFICATION_ID, notification)
        } catch (e: SecurityException) {
            // Handle permission denied
        }
    }

    companion object {
        const val CHANNEL_ID = "movie_reminders"
        const val EXTRA_MOVIE_ID = "movie_id"
        const val EXTRA_MOVIE_TITLE = "movie_title"
        const val EXTRA_MOVIE_CONTENT = "movie_content"
        const val ACTION_DISMISS = "com.madememagic.limelight.DISMISS"
        const val AUTH_NOTIFICATION_ID = 999999
    }
}
