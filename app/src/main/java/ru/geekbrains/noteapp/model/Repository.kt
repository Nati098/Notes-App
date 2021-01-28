package ru.geekbrains.noteapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.geekbrains.noteapp.model.data.Color
import ru.geekbrains.noteapp.model.data.Note
import java.util.*

object Repository {

    private val notes: MutableList<Note> = mutableListOf(
        Note(UUID.randomUUID().toString(), "title1", "sofsdsdnskf", Color.YELLOW),
        Note(UUID.randomUUID().toString(), "title2", "dsfoenenen", Color.PURPLE),
        Note(UUID.randomUUID().toString(), "title3", "asdasdavewe", Color.WHITE)
    )

    private val notesLiveData = MutableLiveData<List<Note>>()

    init {
        notesLiveData.value = notes
    }

    fun getNotes(): LiveData<List<Note>> = notesLiveData

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note) {
        for (i in 0 until notes.size) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }

        notes.add(note)
    }
}