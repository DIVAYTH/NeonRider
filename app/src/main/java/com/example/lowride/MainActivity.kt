package com.example.lowride

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.ViewCompat
import com.example.lowride.controllerAndMusic.MediaService
import kotlinx.android.synthetic.main.activity_main.*

lateinit var prefs: SharedPreferences
val CAR_POSITION_LEFT: Float = SizeDetermination.getCarPositionLeft()
val CAR_POSITION_RIGHT: Float = SizeDetermination.getCarPositionRight()
val MILLS: Long = 4000
val SETTINGS: String = "settings"
val SCORE = "score"
val ACTION: String = "action"
val PAUSE: String = "pause"
val PLAY: String = "play"

class MainActivity : AppCompatActivity() {
    val handler: Handler = Handler(Looper.getMainLooper())
    lateinit var runnable: Runnable
    var modeCar: Boolean = true
    var modeY: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Получаем рассширение дисплея
        SizeDetermination.getSizeDisplay(resources, this)

        // Создаем файл для хранения максимального результата
        prefs = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

        // Устанавливаем максимальный результат
        maxScore.setText("max score: " + prefs.getInt(SCORE, 0))

        val Y_LINE1: Float = car.translationY
        val Y_LINE2: Float = car.translationY + SizeDetermination.getYline2Margin()
        val Y_LINE3: Float = car.translationY + SizeDetermination.getYline3Margin()

        runnable = Runnable {
            chooseAnimation(Y_LINE1, Y_LINE2, Y_LINE3)
        }

        handler.post(runnable)

        startButton.setOnClickListener {
            startGame()
        }
    }

    fun startGame() {
        startService(
            Intent(this, MediaService::class.java)
                .putExtra(ACTION, PLAY)
        )
        startActivity(Intent(this, StartGameActivity::class.java))
        finish()
    }

    fun chooseAnimation(Y_LINE1: Float, Y_LINE2: Float, Y_LINE3: Float) {
        when (modeY) {
            1 -> setCarY(Y_LINE1, 2)
            2 -> setCarY(Y_LINE2, 3)
            3 -> setCarY(Y_LINE3, 1)
        }
        if (modeCar) {
            drawAnimation(CAR_POSITION_RIGHT, R.drawable.car_1, CAR_POSITION_LEFT)
            modeCar = false
        } else {
            drawAnimation(CAR_POSITION_LEFT, R.drawable.car_1_mode, CAR_POSITION_RIGHT)
            modeCar = true
        }
        handler.postDelayed(runnable, MILLS)
    }

    fun drawAnimation(distance: Float, skinCar: Int, corX: Float) {
        car.setImageResource(skinCar)
        car.translationX = corX
        ViewCompat.animate(car)
            .setDuration(MILLS)
            .translationX(distance)
    }

    fun setCarY(corY: Float, mode: Int) {
        car.translationY = corY
        modeY = mode
    }

    override fun onPause() {
        super.onPause();
        startService(
            Intent(this, MediaService::class.java).putExtra(ACTION, PAUSE)
        )
    }

    override fun onResume() {
        super.onResume()
        startService(
            Intent(this, MediaService::class.java).putExtra(ACTION, PLAY)
        )
    }
}