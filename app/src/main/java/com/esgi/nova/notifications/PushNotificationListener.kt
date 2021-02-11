package com.esgi.nova.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.esgi.nova.R
import com.esgi.nova.application_state.application.SetSynchronizeState
import com.esgi.nova.parameters.application.HasNotifications
import com.esgi.nova.ui.init.InitSetupActivity
import com.esgi.nova.users.ui.LoginActivity
import com.microsoft.windowsazure.messaging.notificationhubs.NotificationListener
import com.microsoft.windowsazure.messaging.notificationhubs.NotificationMessage

class PushNotificationListener(
    private val hasNotifications: HasNotifications,
    private val setSynchronizeState: SetSynchronizeState
) :
    NotificationListener {

    companion object {
        const val CHANNEL_ID = "PushNotification"
        const val NOTIFICATION_ID = 1

        fun createNotificationChannel(context: Context) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onPushNotificationReceived(context: Context?, message: NotificationMessage?) {

        if (context == null) return
        val notificationBuilder = getBaseNotificationBuilder(context, message?.title, message?.body)
        handleNotificationByType(message = message)

        if (!hasNotifications.execute()) return
        customizeNotificationBuilderByType(
            context = context,
            notificationBuilder = notificationBuilder,
            message = message
        )
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun customizeNotificationBuilderByType(
        context: Context,
        notificationBuilder: NotificationCompat.Builder,
        message: NotificationMessage?
    ): NotificationCompat.Builder? {
        return when (message?.data?.values?.firstOrNull()) {
            NotificationType.UPDATE.name -> {
                transformBuilderForUpdate(context, notificationBuilder)
            }
            else -> notificationBuilder
        }

    }

    private fun handleNotificationByType(message: NotificationMessage?) {
        when (message?.data?.values?.firstOrNull()) {
            NotificationType.UPDATE.name -> {
                setSynchronizeState.execute(false)
            }
        }
    }

    private fun getBaseNotificationBuilder(
        context: Context,
        title: String?,
        content: String?
    ): NotificationCompat.Builder {
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, LoginActivity.getStartIntent(context), 0)
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_feedback_black_24dp)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)

    }


    private fun transformBuilderForUpdate(
        context: Context,
        builder: NotificationCompat.Builder
    ): NotificationCompat.Builder? {
        val pendingIntent =
            PendingIntent.getActivity(context, 0, InitSetupActivity.getStartIntent(context), 0)
        return builder.addAction(
            R.drawable.round_system_update_alt_black_24dp,
            context.getString(R.string.launch_update),
            pendingIntent
        )
            .setAutoCancel(true)
    }


}