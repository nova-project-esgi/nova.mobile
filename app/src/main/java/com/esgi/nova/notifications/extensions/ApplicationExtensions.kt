package com.esgi.nova.notifications.extensions

import android.app.Application
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.application_state.application.SetSynchronizeState
import com.esgi.nova.notifications.PushNotificationListener
import com.esgi.nova.notifications.NotificationConstants
import com.esgi.nova.parameters.application.HasNotifications
import com.microsoft.windowsazure.messaging.ConnectionString
import com.microsoft.windowsazure.messaging.notificationhubs.NotificationHub
import java.net.URI

fun Application.startNotificationsListening(hasNotifications: HasNotifications, setSynchronizeState: SetSynchronizeState) {
    NotificationHub.setListener(PushNotificationListener(hasNotifications,setSynchronizeState))
    NotificationHub.start(
        this,
        NotificationConstants.NhName,
        ConnectionString.createUsingSharedAccessKey(
            URI(NotificationConstants.NhEndpoint),
            NotificationConstants.NhSharedAccessKeyName,
            NotificationConstants.NhSharedAccessKey
        )
    )
}

