package br.com.cassfer.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import br.com.cassfer.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calcularGorjeta() }
        binding.costOfServiceEditText.setOnKeyListener{view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun calcularGorjeta() {
        val valorServico = binding.costOfServiceEditText.text.toString().toDoubleOrNull()

        val percentGorjeta = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_20_porcento -> 0.20
            R.id.option_18_porcento -> 0.18
            else -> 0.15
        }

        if(valorServico == null){
            binding.tipResult.text = ""
            return
        }

        var gorjeta = valorServico.times(percentGorjeta)

        val arredondar = binding.roundUpSwitch.isChecked

        if(arredondar){
            gorjeta = kotlin.math.ceil(gorjeta)
        }

        val gorjetaFormatada = NumberFormat.getCurrencyInstance().format(gorjeta)

        binding.tipResult.text = getString(R.string.text_tip_result, gorjetaFormatada)

    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean{
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}