package com.andruid.magic.newsdaily.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.andruid.magic.newsdaily.R
import com.andruid.magic.newsdaily.data.Constants
import com.andruid.magic.newsdaily.ui.activity.HomeActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object NotificationUtil {
    private const val CHANNEL_ID = "channel_headlines"

    @SuppressLint("DefaultLocale", "WrongConstant")
    suspend fun buildNotification(
        context: Context, icon: Int, category: String,
        metadataCompat: MediaMetadataCompat, token: MediaSessionCompat.Token
    ): NotificationCompat.Builder {
        val intent = Intent(context, HomeActivity::class.java)
            .setAction(Constants.ACTION_NOTI_CLICK)
            .setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
            .putExtra(Constants.EXTRA_CATEGORY, category)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            NotificationManager.IMPORTANCE_HIGH
        else 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    Constants.CHANNEL_NAME,
                    importance
                ).apply {
                    enableLights(true)
                    lightColor = Color.GREEN
                }
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(token)
                    .setShowActionsInCompactView(0, 1, 2)
                    .setShowCancelButton(true)
            )
            setSmallIcon(R.mipmap.ic_launcher)
            priority = NotificationCompat.PRIORITY_MAX
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setOnlyAlertOnce(true)
            setContentIntent(
                PendingIntent.getActivity(
                    context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            setContentTitle(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_TITLE))
            setContentText(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM))
            setSubText(category.capitalize())
            setShowWhen(true)

            var pendingIntent = MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            )
            addAction(android.R.drawable.ic_media_previous, "previous", pendingIntent)

            pendingIntent = MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_PLAY_PAUSE
            )
            addAction(icon, "play", pendingIntent)

            pendingIntent = MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            )
            addAction(android.R.drawable.ic_media_next, "next", pendingIntent)

            val albumArtUri =
                metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI)
            try {
                withContext(Dispatchers.IO) { setLargeIcon(Picasso.get().load(albumArtUri).get()) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}