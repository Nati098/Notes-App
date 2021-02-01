package ru.geekbrains.noteapp.viewmodel.viewstate

import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.viewmodel.viewstate.CustomViewState

class BaseViewState(val notes: List<Note>? = null, error: Throwable? = null) : CustomViewState<List<Note>?>(notes, error)