package com.example.myapp009amynotehub

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp009amynotehub.data.Note
import com.example.myapp009amynotehub.data.NoteDao
import com.example.myapp009amynotehub.data.NoteHubDatabaseInstance
import com.example.myapp009amynotehub.databinding.ActivityEditNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var noteDao: NoteDao
    private var noteId: Int = -1
    private var currentCreatedAt: Long = System.currentTimeMillis()
    private var currentColor: String = "yellow"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteDao = NoteHubDatabaseInstance.getDatabase(this).noteDao()

        // Spinner pro kategorie
        val categories = listOf("Obecné", "Škola", "Práce", "Osobní")
        val spinnerAdapter = ArrayAdapter(
            this,
            R.layout.simple_spinner_item,
            categories
        )
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerEditCategory.adapter = spinnerAdapter

        // Získáme ID poznámky z Intentu a zobrazíme ho
        noteId = intent.getIntExtra("note_id", -1)
        binding.tvEditNoteId.text = "ID poznámky: $noteId"

        // Načteme poznámku z DB včetně priority a kategorie
        lifecycleScope.launch {
            noteDao.getAllNotes().collect { notes ->
                val note = notes.find { it.id == noteId }
                if (note != null) {
                    currentCreatedAt = note.createdAt
                    currentColor = note.color
                    binding.etEditTitle.setText(note.title)
                    binding.etEditContent.setText(note.content)
                    binding.etEditPriority.setText(note.priority)

                    val categoryIndex = categories.indexOf(note.category)
                    if (categoryIndex >= 0) {
                        binding.spinnerEditCategory.setSelection(categoryIndex)
                    }
                }
            }
        }

        // Kliknutí na Uložit
        binding.btnSaveChanges.setOnClickListener {
            val updatedTitle = binding.etEditTitle.text.toString()
            val updatedContent = binding.etEditContent.text.toString()
            val updatedPriority = binding.etEditPriority.text.toString().ifBlank { "Střední" }
            val updatedCategory = binding.spinnerEditCategory.selectedItem.toString()

            val updatedNote = Note(
                id = noteId,
                title = updatedTitle,
                content = updatedContent,
                priority = updatedPriority,
                category = updatedCategory,
                createdAt = currentCreatedAt,
                color = currentColor
            )

            lifecycleScope.launch(Dispatchers.IO) {
                noteDao.update(updatedNote)
                finish()
            }
        }
    }
}
