package ru.geekbrains.noteapp.viewmodel.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.geekbrains.noteapp.model.Repository
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.model.firebase.NoteResult
import ru.geekbrains.noteapp.viewmodel.viewstate.NoteViewState

class NoteViewModel(private val repository: Repository = Repository) : CustomViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }

    fun loadNote(id: String) {
        repository.getNoteById(id).observeForever (object : Observer<NoteResult> {
            override fun onChanged(t: NoteResult?) {
                if (t == null) return

                when (t) {
                    is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(t.data as? Note)
                    is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = t.error)
                }
            }
        })
    }
}