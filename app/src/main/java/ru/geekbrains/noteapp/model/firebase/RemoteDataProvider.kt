package ru.geekbrains.noteapp.model.firebase

import androidx.lifecycle.LiveData
import ru.geekbrains.noteapp.model.data.Note

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>

}