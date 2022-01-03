package com.example.lowride.controllerAndMusic

import android.app.Service

import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.lowride.R

val ACTION: String = "action"
val PAUSE: String = "pause"
val PLAY: String = "play"

class MediaService : Service() {
    lateinit var song: MediaPlayer
    var currentSongPosition: Int = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        song = MediaPlayer.create(this, R.raw.cyber_wave)
        song.isLooping = true
        song.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getStringExtra(ACTION) == PAUSE) {
            stopSong()
        } else if (intent?.getStringExtra(ACTION) == PLAY) {
            playSong()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun stopSong() {
        if (song.isPlaying) {
            song.stop();
            currentSongPosition = song.currentPosition
        }
    }

    fun playSong() {
        if (song.isPlaying) {
            song.stop();
            song.prepare()
            song.seekTo(0)
        } else {
            song.prepare()
            song.seekTo(currentSongPosition)
        }
        song.start();
    }
}