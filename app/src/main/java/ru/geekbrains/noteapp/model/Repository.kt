package ru.geekbrains.noteapp.model

import ru.geekbrains.noteapp.model.data.Color
import ru.geekbrains.noteapp.model.data.Note
import java.util.*

object Repository {

    val notes: MutableList<Note> = mutableListOf(
        Note(UUID.randomUUID().toString(), "title1", "sofsdsdnskf", Color.YELLOW),
        Note(UUID.randomUUID().toString(), "title2", "dsfoenenen", Color.PURPLE),
        Note(UUID.randomUUID().toString(), "title3", "asdasdavewe", Color.WHITE)
    )

}