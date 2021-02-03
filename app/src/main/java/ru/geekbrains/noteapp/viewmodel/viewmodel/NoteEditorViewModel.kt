package ru.geekbrains.noteapp.viewmodel.viewmodel

import ru.geekbrains.noteapp.model.Repository
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.model.firebase.NoteResult
import ru.geekbrains.noteapp.viewstate.NoteEditorViewState


class NoteEditorViewModel(private val repository: Repository = Repository) : BaseViewModel<Note?, NoteEditorViewState>() {

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
        repository.getNoteById(id).observeForever { t: NoteResult? ->
            if (t == null) return@observeForever
            when (t) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteEditorViewState(t.data as? Note)
                is NoteResult.Error -> viewStateLiveData.value = NoteEditorViewState(error = t.error)
            }
        }
    }
}