package ru.geekbrains.noteapp.viewmodel.viewmodel

import androidx.lifecycle.Observer
import ru.geekbrains.noteapp.viewstate.NoteListViewState
import ru.geekbrains.noteapp.model.Repository
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.model.firebase.NoteResult


class NoteListViewModel(private val repository: Repository = Repository) : BaseViewModel<List<Note>?, NoteListViewState>() {

    private val repoNotes = repository.getNotes()
    private val notesObserver = object : Observer<NoteResult> {
        override fun onChanged(t: NoteResult?) {
            if (t == null) return

            when (t) {
                is NoteResult.Success<*> -> viewStateLiveData.value =
                    NoteListViewState(t.data as? List<Note>)
                is NoteResult.Error -> viewStateLiveData.value =
                    NoteListViewState(error = t.error)
            }
        }
    }

    init {
        viewStateLiveData.value =
            NoteListViewState()
        repoNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repoNotes.removeObserver(notesObserver)
    }

}