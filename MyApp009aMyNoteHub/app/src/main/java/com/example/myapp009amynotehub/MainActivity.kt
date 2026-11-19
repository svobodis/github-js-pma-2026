package com.example.myapp009amynotehub

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp009amynotehub.data.Note
import com.example.myapp009amynotehub.data.NoteDao
import com.example.myapp009amynotehub.data.NoteHubDatabaseInstance
import com.example.myapp009amynotehub.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteDao: NoteDao
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchQuery = MutableStateFlow("")

        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        // DAO
        noteDao = NoteHubDatabaseInstance.getDatabase(applicationContext).noteDao()

        // Adapter s callbacky
        adapter = NoteAdapter(
            onEditClick = { note ->
                val intent = Intent(this, EditNoteActivity::class.java)
                intent.putExtra("note_id", note.id)
                startActivity(intent)
            },
            onDeleteClick = { note ->
                showDeleteConfirmationDialog(note)
            }
        )

        // RecyclerView
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNotes.adapter = adapter

        // SearchView listener
        binding.searchViewNotes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery.value = newText ?: ""
                return true
            }
        })

        // Flow collection for search
        lifecycleScope.launch {
            searchQuery.collectLatest { text ->
                noteDao.searchNotes(text).collectLatest { notes ->
                    adapter.submitList(notes)
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog(note: Note) {
        AlertDialog.Builder(this)
            .setTitle("Smazání poznámky")
            .setMessage("Opravdu chcete smazat tuto poznámku?")
            .setPositiveButton("ANO") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    noteDao.delete(note)
                }
            }
            .setNegativeButton("NE") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
