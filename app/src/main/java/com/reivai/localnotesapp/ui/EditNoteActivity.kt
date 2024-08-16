package com.reivai.localnotesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.reivai.localnotesapp.R
import com.reivai.localnotesapp.database.Note
import com.reivai.localnotesapp.databinding.ActivityEditNoteBinding
import com.reivai.localnotesapp.viewmodel.NoteViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private var noteId: Int? = null
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        noteId = intent.getIntExtra("note_id", -1)

        noteId?.let {id ->
            if (id != -1) {
                noteViewModel.getNoteById(id).observe(this, Observer { note ->
                    note?.let {
                        this.note = it
                        binding.etTitle.setText(it.title)
                        binding.etDescription.setText(it.description)
                        binding.tvDate.text = it.date
                    }
                })
            }

        }

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dateTime = LocalDateTime.now().format(formatter)
        val date = dateTime.toString()
        binding.tvDate.text = date

        binding.fabSaveNote.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val date = dateTime.toString()
            val description= binding.etDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val note = if (noteId != null && noteId != -1) {
                    Note(id = noteId!!, title = title, description = description, date = date)
                } else {
                    Note(title = title, description = description, date = date)
                }
                noteViewModel.insert(note)
                finish()
            }
            else if (title.isEmpty()) {
                Toast.makeText(this, "Silahkan isi title dulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_delete -> {
                deleteNote()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteNote() {
        note?.let {
            noteViewModel.delete(it)
            finish()
        }
    }
}