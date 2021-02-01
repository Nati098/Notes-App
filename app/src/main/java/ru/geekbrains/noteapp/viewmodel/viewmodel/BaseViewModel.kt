package ru.geekbrains.noteapp.viewmodel.viewmodel

import androidx.lifecycle.Observer
import ru.geekbrains.noteapp.viewmodel.viewstate.BaseViewState
import ru.geekbrains.noteapp.model.Repository
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.model.firebase.NoteResult

class BaseViewModel(private val repository: Repository = Repository) : CustomViewModel<List<Note>?, BaseViewState>() {

    private val repoNotes = repository.getNotes()
    private val notesObserver = object : Observer<NoteResult> {
        override fun onChanged(t: NoteResult?) {
            if (t == null) return

            when (t) {
                is NoteResult.Success<*> -> viewStateLiveData.value =
                    BaseViewState(t.data as? List<Note>)
                is NoteResult.Error -> viewStateLiveData.value =
                    BaseViewState(error = t.error)
            }
        }
    }

    init {
        viewStateLiveData.value =
            BaseViewState()
        repoNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repoNotes.removeObserver(notesObserver)
    }

    //    init {
//        viewStateLiveData.value = BaseViewState()
//    }
//
//    fun changeState(notes: List<Note>) {
//        viewStateLiveData.value = BaseViewState(notes)
//    }
}