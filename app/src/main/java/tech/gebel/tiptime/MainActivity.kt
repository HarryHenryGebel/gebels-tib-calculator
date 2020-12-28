package tech.gebel.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.gebel.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {

        if (costOfService == null) {
            binding.tipResult.text = ""
            return
        }
        // Extract cost of service or return if invalid. Convert to cents.
        val costOfServiceEntry = binding.costOfServiceText.text.toString().toDoubleOrNull() ?: return
        val costOfService = (costOfServiceEntry * 100).toInt()

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        val tip = if (binding.roundUpSwitch.isChecked)
            kotlin.math.ceil(tipPercentage * costOfService)
        else
            tipPercentage * costOfService

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)


    }
}
