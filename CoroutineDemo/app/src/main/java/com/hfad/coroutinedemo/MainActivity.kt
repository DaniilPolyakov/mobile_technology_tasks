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

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    //    Метод для вывода информации о количестве запускаемых корутин
    //    Выполнение корутин с помощью главного диспетчера
    suspend fun performTask(tasknumber: Int): Deferred<String> =
        coroutineScope.async(Dispatchers.Main) {
            delay(5_000)
            return@async "Завершен запуск ${tasknumber} корутин"
        }

    //    Метод выполняющийся по нажатии кнопки, выполняющий цикл запусков запрошенного числа корутинов

    fun launchCoroutines(view: View) {
        (1..count).forEach {
            binding.statusText.text = "Будет запущено Корутин ${it}"
            coroutineScope.launch(Dispatchers.Main) {
                binding.statusText.text = performTask(it).await()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBar.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener {

    //      метод для визуализации количества корутин для запуска в текстовом поле

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