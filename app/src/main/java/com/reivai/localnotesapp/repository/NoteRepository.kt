package com.reivai.localnotesapp.repository

import androidx.lifecycle.LiveData
import com.reivai.localnotesapp.database.Note
import com.reivai.localnotesapp.database.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    val getAllNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    fun getNoteById(id: Int): LiveData<Note> {
        return noteDao.getNoteById(id)
    }
}