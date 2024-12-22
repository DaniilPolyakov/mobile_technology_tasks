package com.hfad.coroutinesdemo

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputBinding
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hfad.coroutinedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //    Объявили переменные требуемые для хранения количества корутин для запуска из виджета SeekBar
    private lateinit var binding: ActivityMainBinding
    private var count: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBar.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener {

//            метод для визуализации количества корутин для запуска в текстовом поле

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                count = progress
                binding.countText.text = "${count} корутин"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

    }
}