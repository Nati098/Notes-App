package ru.geekbrains.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import ru.geekbrains.noteapp.model.Repository
import ru.geekbrains.noteapp.model.data.Note

class NoteViewModel(private val repository: Repository = Repository) : ViewModel() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }
}