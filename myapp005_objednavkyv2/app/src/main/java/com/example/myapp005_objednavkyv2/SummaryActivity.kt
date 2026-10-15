package com.example.myapp005_objednavkyv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp005_objednavkyv2.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace ViewBindingu
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ochrana před překryvem systémovými lištami
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Přidá vnitřní okraj (padding) odpovídající velikosti horní a spodní systémové lišty
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val selectedBallName = intent.getStringExtra("EXTRA_BALL_NAME")
        val isGrip = intent.getBooleanExtra("EXTRA_GRIB", false)
        val isGlue = intent.getBooleanExtra("EXTRA_GLUE", false)
        val isBag = intent.getBooleanExtra("EXTRA_BAG", false)
        val totalPrice = intent.getIntExtra("EXTRA_TOTAL_PRICE", 0)

        binding.tvSelectedBike.text = "Vybraný míč: $selectedBallName"

        val extrasList = mutableListOf<String>()
        if (isGrip) extrasList.add("Grip")
        if (isGlue) extrasList.add("Glue")
        if (isBag) extrasList.add("Bag")

        binding.tvExtras.text = if (extrasList.isEmpty()) {
            "Příplatková výbava:\n- žádná"
        } else {
            "Příplatková výbava:\n" + extrasList.joinToString("\n") { "- $it" }
        }

        binding.tvFinalPrice.text = "Celková cena: $totalPrice Kč"
        binding.btnBack.setOnClickListener { finish() }
    }
}
