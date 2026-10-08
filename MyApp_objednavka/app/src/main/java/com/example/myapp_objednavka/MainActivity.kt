package com.example.myapp_objednavka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp_objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rgBalls.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbBall1 -> binding.ivBall.setImageResource(R.drawable.ball_1)
                R.id.rbBall2 -> binding.ivBall.setImageResource(R.drawable.ball_2)
                R.id.rbBall3 -> binding.ivBall.setImageResource(R.drawable.ball_3)
            }
        }

        binding.btnIncrease.setOnClickListener {
            quantity++
            binding.tvQuantity.text = quantity.toString()
        }

        binding.btnDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
            }
        }

        binding.btnOrder.setOnClickListener {
            var unitPrice = 0

            val selectedBall = when (binding.rgBalls.checkedRadioButtonId) {
                R.id.rbBall1 -> { unitPrice = 450; binding.rbBall1.text }
                R.id.rbBall2 -> { unitPrice = 550; binding.rbBall2.text }
                R.id.rbBall3 -> { unitPrice = 650; binding.rbBall3.text }
                else -> { unitPrice = 450; binding.rbBall1.text }
            }

            val addonsList = mutableListOf<CharSequence>()
            if (binding.cbGrip.isChecked) {
                addonsList.add(binding.cbGrip.text)
                unitPrice += 100
            }
            if (binding.cbGlue.isChecked) {
                addonsList.add(binding.cbGlue.text)
                unitPrice += 150
            }
            if (binding.cbBag.isChecked) {
                addonsList.add(binding.cbBag.text)
                unitPrice += 200
            }

            val addonsText = if (addonsList.isNotEmpty()) {
                addonsList.joinToString(", ")
            } else {
                getString(R.string.addons_none)
            }

            val totalPrice = unitPrice * quantity

            binding.tvOrderResult.text = getString(
                R.string.order_summary_result,
                selectedBall,
                addonsText,
                quantity,
                totalPrice
            )
        }

        binding.btnReset.setOnClickListener {
            binding.rbBall1.isChecked = true
            binding.ivBall.setImageResource(R.drawable.ball_1)
            binding.cbGrip.isChecked = false
            binding.cbGlue.isChecked = false
            binding.cbBag.isChecked = false
            quantity = 1
            binding.tvQuantity.text = "1"
            binding.tvOrderResult.setText(R.string.order_summary_default)
        }
    }
}