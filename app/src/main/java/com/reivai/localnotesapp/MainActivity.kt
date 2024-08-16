package com.reivai.localnotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.reivai.localnotesapp.adapter.NoteAdapter
import com.reivai.localnotesapp.databinding.ActivityMainBinding
import com.reivai.localnotesapp.ui.EditNoteActivity
import com.reivai.localnotesapp.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAdapter = NoteAdapter { note ->
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra("note_id", note.id)
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = noteAdapter
        }

        noteViewModel.allNotes.observe(this) { notes ->
            notes?.let { noteAdapter.setNotes(it) }
        }

        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            startActivity(intent)
        }
    }
}