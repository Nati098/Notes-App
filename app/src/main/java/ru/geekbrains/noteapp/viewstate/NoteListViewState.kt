package ru.geekbrains.noteapp.viewstate

import ru.geekbrains.noteapp.model.data.Note

class NoteListViewState(val notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)