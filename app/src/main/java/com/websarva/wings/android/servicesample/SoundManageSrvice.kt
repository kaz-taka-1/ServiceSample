package com.websarva.wings.android.servicesample

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.view.View

class SoundManageService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    private var _player: MediaPlayer? = null
    override fun onCreate(){
        _player = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mediaFileUriStr = "android:resource://&{packageName}/${R.raw.mountain_stream}"
        val mediaFileUri = Uri.parse(mediaFileUriStr)
        _player?.let{
            it.setDataSource(this@SoundManageService,mediaFileUri)
            it.setOnPreparedListener(PlayerPreparedListener())
            it.setOnCompletionListener(PlayerCompletionListener())
            it.prepareAsync()
        }
        return START_NOT_STICKY
    }
    override fun onDestroy(){
        _player?.let{
            if(it.isPlaying){
                it.stop()
            }
            it.release()
        }
        _player = null
    }
    private inner class PlayerPreparedListener :MediaPlayer.OnPreparedListener{
        override fun onPrepared(mp: MediaPlayer?) {
            mp.start()
        }
    }
    private inner class PlayerCompletionListener :MediaPlayer.OnCompletionListener{
        override fun onCompletion(mp: MediaPlayer?) {
            stopSelf()
        }
    }

}
