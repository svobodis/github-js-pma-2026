package com.example.myapp008asharedpref

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapp008asharedpref.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("GameProfile", Context.MODE_PRIVATE)

        val savedName = sharedPref.getString("NICKNAME", "")
        val isHardcore = sharedPref.getBoolean("HARDCORE", false)

        binding.etNickname.setText(savedName)
        binding.switchHardcoreMode.isChecked = isHardcore

        binding.btnSave.setOnClickListener {
            val currentName = binding.etNickname.text.toString()
            val currwntHardcore = binding.switchHardcoreMode.isChecked

            val editor = sharedPref.edit()
            editor.putString("NICKNAME", currentName)
            editor.putBoolean("HARDCORE", currwntHardcore)
            editor.commit()

            Toast.makeText(requireContext(), "Uložení proběhlo úspěšně", Toast.LENGTH_SHORT).show()
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}