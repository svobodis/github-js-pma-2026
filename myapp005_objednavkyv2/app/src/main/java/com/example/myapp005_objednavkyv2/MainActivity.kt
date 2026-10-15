package com.example.myapp005_objednavkyv2

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp005_objednavkyv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
        updatePrice()
    }

    private fun setupListeners() {
        // Společný listener pro RadioGroup: aktualizuje cenu I obrázek
        binding.rgBalls.setOnCheckedChangeListener { _, checkedId ->
            updatePrice()
            when (checkedId) {
                R.id.rbball1 -> binding.imageView.setImageResource(R.drawable.ball_1)
                R.id.rbball2 -> binding.imageView.setImageResource(R.drawable.ball_2)
                R.id.rbball3 -> binding.imageView.setImageResource(R.drawable.ball_3)
            }
        }

        // Aktualizace ceny při změně checkboxů
        binding.cbGrib.setOnCheckedChangeListener { _, _ -> updatePrice() }
        binding.cbglue.setOnCheckedChangeListener { _, _ -> updatePrice() }
        binding.cbbag.setOnCheckedChangeListener { _, _ -> updatePrice() }

        // Přechod na souhrn po kliknutí na tlačítko
        binding.button.setOnClickListener {
            val selectedBallId = binding.rgBalls.checkedRadioButtonId
            val selectedBallText = findViewById<RadioButton>(selectedBallId)?.text?.toString() ?: "Nezvoleno"

            val intent = Intent(this, SummaryActivity::class.java).apply {
                putExtra("EXTRA_BALL_NAME", selectedBallText)
                putExtra("EXTRA_GRIB", binding.cbGrib.isChecked)
                putExtra("EXTRA_GLUE", binding.cbglue.isChecked)
                putExtra("EXTRA_BAG", binding.cbbag.isChecked)
                putExtra("EXTRA_TOTAL_PRICE", calculateTotalPrice())
            }
            startActivity(intent)
        }
    }

    private fun calculateTotalPrice(): Int {
        var price = when (binding.rgBalls.checkedRadioButtonId) {
            R.id.rbball1 -> 500
            R.id.rbball2 -> 600
            R.id.rbball3 -> 800
            else -> 0
        }
        if (binding.cbGrib.isChecked) price += 200
        if (binding.cbglue.isChecked) price += 100
        if (binding.cbbag.isChecked) price += 2000
        return price
    }

    private fun updatePrice() {
        val totalPrice = calculateTotalPrice()
        binding.textView3.text = "Cena $totalPrice Kč"
    }
}