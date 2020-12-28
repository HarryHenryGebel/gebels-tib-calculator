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
        // Clear tip result whether successful or not
        binding.tipResult.text = ""

        // Extract cost of service or return if invalid. Convert to cents.
        val costOfServiceEntry = binding.costOfServiceText.text.toString().toDoubleOrNull() ?: return
        val costOfService = (costOfServiceEntry * 100).toInt()

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = (tipPercentage * costOfService).toInt()
        var total = tip + costOfService

        // if rounding, round total to next unit, then adjust tip accordingly
        if (binding.roundUpSwitch.isChecked) {
            total = ceil((total.toDouble() / 100)).toInt() * 100
            tip = total - costOfService
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip.toDouble() / 100)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}
