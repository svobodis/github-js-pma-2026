package com.example.myapp07

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp07.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Výchozí zobrazení prvního fragmentu
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RecipeListFragment())
                .commit()
        }

        // 1. Fragment - Seznam receptů
        binding.btnFragmentA.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RecipeListFragment())
                .commit()
        }

        // 2. Fragment - Detail receptu
        binding.btnFragmentB.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RecipeDetailFragment())
                .commit()
        }

        // 3. Fragment - Oblíbené
        binding.btnFragmentC.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FavoritesFragment())
                .commit()
        }
    }
}