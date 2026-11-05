package com.example.myapp008bdatastore

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapp008bdatastore.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "gamer_profile")

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val NICKNAME_KEY = stringPreferencesKey("NICKNAME")
    private val HARDCORE_KEY = booleanPreferencesKey("HARDCORE")

    private var clickCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val preferences = requireContext().dataStore.data.first()
            binding.etNickname.setText(preferences[NICKNAME_KEY] ?: "")
            binding.switchHardcoreMode.isChecked = preferences[HARDCORE_KEY] ?: false
        }

        binding.btnClicker.setOnClickListener {
            clickCount++
            binding.tvClickerCount.text = "Počet kliků během čekání: $clickCount"
        }

        binding.btnSave.setOnClickListener {
            val currentName = binding.etNickname.text.toString()
            val currentHardcore = binding.switchHardcoreMode.isChecked

            viewLifecycleOwner.lifecycleScope.launch {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnSave.isEnabled = false

                delay(3000)

                requireContext().dataStore.edit { prefs ->
                    prefs[NICKNAME_KEY] = currentName
                    prefs[HARDCORE_KEY] = currentHardcore
                }

                binding.progressBar.visibility = View.INVISIBLE
                binding.btnSave.isEnabled = true

                // Uložení finálního skóre před resetem
                val finalScore = clickCount

                // Reset počítadla a textu na obrazovce
                clickCount = 0
                binding.tvClickerCount.text = "Počet kliků během čekání: 0"

                // Zobrazení Snackbaru s výpisem skóre
                Snackbar.make(
                    binding.root,
                    "Uloženo! Vaše skóre během načítání: $finalScore kliků",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Zpět") {
                        Toast.makeText(requireContext(), "Akce vrácena zpět!", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}