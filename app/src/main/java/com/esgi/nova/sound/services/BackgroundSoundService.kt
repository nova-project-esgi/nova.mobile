package com.esgi.nova.sound.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.esgi.nova.R
import com.esgi.nova.parameters.application.HasSoundOn
import com.esgi.nova.sound.application.GetSoundResume
import com.esgi.nova.sound.application.SaveSoundResume
import com.esgi.nova.sound.infrastructure.storage.models.SoundResume
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject


@AndroidEntryPoint
class BackgroundSoundService  : Service() {
    var player: MediaPlayer? = null

    @Inject
    lateinit var hasSoundOn: HasSoundOn
    @Inject
    lateinit var getSoundResume: GetSoundResume
    @Inject
    lateinit var saveSoundResume: SaveSoundResume

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, BackgroundSoundService::class.java)
            context.startService(intent)
        }
        fun stop(context: Context) {
            val intent = Intent(context, BackgroundSoundService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.background_sound)
        player?.let { player ->
            player.isLooping = true // Set looping
            player.setVolume(100f, 100f)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(hasSoundOn.execute()){
            val resume = getSoundResume.execute()
            player?.let {player ->
                player.seekTo(resume.backgroundSoundPosition)
                player.start()
            }
        }else {
            stopSelf()
        }
        return  super.onStartCommand(intent, flags, startId)
    }



    override fun onDestroy() {
        player?.let { player ->
            saveSoundResume.execute(SoundResume(player.currentPosition))
            player.stop()
            player.release()
        }
    }

}