package com.example.myapp009amynotehub

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp009amynotehub.data.Note
import com.example.myapp009amynotehub.data.NoteHubDatabaseInstance
import com.example.myapp009amynotehub.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = NoteHubDatabaseInstance.getDatabase(this)
        val noteDao = db.noteDao()

        // ---------- Kategorie (Spinner) ----------
        val categories = listOf("Obecné", "Škola", "Práce", "Osobní")

        val adapter = ArrayAdapter(
            this,
            R.layout.simple_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerCategory.adapter = adapter

        // ---------- Datum vytvoření ----------
        val now = System.currentTimeMillis()
        val formatted = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            .format(Date(now))

        binding.textCreatedAtInfo.text = "Vytvořeno: $formatted"

        binding.btnSaveNote.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val content = binding.editTextContent.text.toString()
            val category = binding.spinnerCategory.selectedItem.toString()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Vyplň název i obsah", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val priority = binding.etPriority.text.toString().ifBlank { "Střední" }
            val note = Note(title = title, content = content, priority = priority, category = category, createdAt = now)

            // Spustí korutinu na vlákně určeném pro vstupně/výstupní operace (IO),
            // které jsou vhodné pro práci s databází, aby se nezablokovalo UI.
            CoroutineScope(Dispatchers.IO).launch {
                // Uložení poznámky do databáze na vlákně pro IO operace (nezablokuje UI)
                noteDao.insert(note)

                // Po uložení zavře tuto aktivitu a vrátí se zpět na hlavní obrazovku
                finish()
            }
        }

    }
}
