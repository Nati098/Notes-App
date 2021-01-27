package ru.geekbrains.noteapp.model

import ru.geekbrains.noteapp.model.data.Color
import ru.geekbrains.noteapp.model.data.Note

object Repository {
    const val VIEW_MODEL_BUNDLE = "view_model_bundle"

    val notes : List<Note>

    init {
        notes = mutableListOf(Note("title1", "sofsdsdnskf", Color.YELLOW),
                                Note("title2", "dsfoenenen", Color.PURPLE),
                                Note("title3", "asdasdavewe", Color.WHITE))
    }

    object LoggerMode {
        var VERBOSE = true
        var DEBUG = VERBOSE && true
    }
}