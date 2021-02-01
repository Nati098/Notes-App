package ru.geekbrains.noteapp.viewmodel.viewstate

import ru.geekbrains.noteapp.model.data.Note

class NoteViewState (val note: Note? = null, error: Throwable? = null) : CustomViewState<Note?>(note, error)