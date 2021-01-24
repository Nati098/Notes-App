package ru.geekbrains.noteapp.model

import ru.geekbrains.noteapp.model.data.Note

object Repository {

    val notes : List<Note>

    init {
        notes = mutableListOf(Note("title1", "sofsdsdnskf", 0xf9e79f.toInt()),
                                Note("title2", "dsfoenenen", 0xe8daef.toInt()),
                                Note("title3", "asdasdavewe", 0xf4f6f7.toInt()))
    }

}