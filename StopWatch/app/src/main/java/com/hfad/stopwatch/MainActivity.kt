package com.hfad.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    lateinit var stopwatch: Chronometer
    var running = false
    var offset: Long = 0

//    Секция определения констант объекта Bundle

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatch = findViewById<Chronometer>(R.id.stopwatch)

//        Задание значений констант при восстановлении активити

        if (savedInstanceState != null)
        {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            } else setBaseTime()
        }




        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            if (!running)
            {
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }

        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {
            if (running)
            {
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }

        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }


//    Переопределенный метод родительского класса, используемый для сохранения значений констант состояния активити приложения

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    //    Определение поведения приложения при остановке активити приложения (скрытии и повторном открытии)
    //    Переопределенный метод родительского класса, выполняющийся при остановке активности (видимости) приложения

    override fun onStop() {
        super.onStop()
        if (running)
        {
            saveOffset()
            stopwatch.stop()
        }
    }

    //    Переопределенный метод родительского класса, выполняющийся при возобновлении активности (видимости) приложения

    override fun onRestart() {
        super.onRestart()
        if (running)
        {
            setBaseTime()
            stopwatch.start()
            offset = 0
        }
    }

    //    Блок определения поведения работы приложения при переходе на "второй" план, перекрываясь всплывающими перекрывающими активити окнами

    //    Переопределенный метод родительского класса, вызывающися при "переходе" приложения на "второй" план

    override fun onPause()
    {
        super.onPause()
        if (running)
        {
            saveOffset()
            stopwatch.stop()
        }
    }

//    Переопределенный метод родительского класса, вызываемый при "возвращении" активности на "первый" план

    override fun onResume() {
        super.onResume()
        if (running)
        {
            setBaseTime()
            stopwatch.start()
            offset = 0
        }
    }


    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }
}