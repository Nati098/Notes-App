package ru.geekbrains.noteapp.model

import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.model.firebase.RemoteDataProvider
import ru.geekbrains.noteapp.model.firebase.RemoteDataProviderImpl


object Repository {

    private val remoteDataProvider: RemoteDataProvider = RemoteDataProviderImpl()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)

}