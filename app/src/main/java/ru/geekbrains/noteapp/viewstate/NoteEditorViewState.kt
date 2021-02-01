package ru.geekbrains.noteapp.viewstate

import ru.geekbrains.noteapp.model.data.Note

class NoteEditorViewState (val note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)