package ru.geekbrains.noteapp.viewstate

import ru.geekbrains.noteapp.model.data.Note

class BaseViewState(val notes: List<Note>? = null, error: Throwable? = null) : CustomViewState<List<Note>?>(notes, error)