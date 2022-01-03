package com.example.lowride

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import android.os.CountDownTimer
import com.example.lowride.controllerAndMusic.MediaService
import com.example.lowride.game.GameView

class StartGameActivity : Activity() {
    lateinit var gameView: GameView
    lateinit var textPause: TextView;
    lateinit var countDownTimer: CountDownTimer;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = GameView(this)
        setContentView(gameView)

        prefs = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

        var bmp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pause_button)
        bmp = SizeDetermination.getGetPauseButton(bmp)
        val imageButton = ImageButton(this)
        imageButton.setImageBitmap(bmp)

        textPause = TextView(this)
        textPause.setText("PAUSE")
        textPause.textSize = 28f
        textPause.visibility = View.INVISIBLE
        textPause.setTextColor(Color.WHITE)
        textPause.typeface = ResourcesCompat.getFont(this, R.font.wargames)
        textPause.gravity = Gravity.CENTER

        addContentView(
            imageButton,
            SizeDetermination.getGetLPPauseButton()
        )

        addContentView(
            textPause,
            gameView.layoutParams
        )

        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textPause.visibility = View.VISIBLE
                if (millisUntilFinished < 1000)
                    textPause.setText("1")
                else if (millisUntilFinished < 2000)
                    textPause.setText("2")
                else if (millisUntilFinished < 3000)
                    textPause.setText("3")
            }

            override fun onFinish() {
                textPause.setText("PAUSE")
                textPause.visibility = View.INVISIBLE
                gameView.handler.post(gameView.runnable)
            }
        }

        imageButton.x = SizeDetermination.dWidth - bmp.width
        imageButton.setOnClickListener {
            pause()
        }
    }

    fun pause() {
        if (!gameView.gameOver) {
            if (!gameView.pause) {
                gameView.pause = true
                gameView.handler.removeCallbacks(gameView.runnable)
                textPause.visibility = View.VISIBLE
            } else {
                gameView.pause = false
                countDownTimer.start()
            }
        }
    }

    override fun onBackPressed() {
        if (gameView.gameOver == false) {
            gameView.pause = false
            pause()
        } else {
            val editor = prefs.edit()
            if (prefs.getInt(SCORE, 0) < gameView.car.s) {
                editor.putInt(SCORE, gameView.car.s).apply()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause();
        gameView.pause = false
        pause()
        startService(
            Intent(this, MediaService::class.java)
                .putExtra(ACTION, PAUSE)
        )
    }

    override fun onResume() {
        super.onResume()
        startService(
            Intent(this, MediaService::class.java)
                .putExtra(ACTION, PLAY)
        )
    }
}