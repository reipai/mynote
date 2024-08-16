package com.reivai.localnotesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reivai.localnotesapp.database.Note
import com.reivai.localnotesapp.databinding.ItemNoteBinding

class NoteAdapter(private val onNoteClicked: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes = listOf<Note>()

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note, onNoteClicked)
    }

    override fun getItemCount(): Int = notes.size


    class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, onNoteClicked: (Note) -> Unit) {
            binding.tvTitle.text = note.title
            binding.tvDescription.text = note.description
            binding.tvDate.text = note.date
            binding.root.setOnClickListener {
                onNoteClicked(note)
            }
        }
    }
}