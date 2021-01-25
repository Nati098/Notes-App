package ru.geekbrains.noteapp.model.data

import java.io.Serializable

class Note (val title : String,
            val content : String,
            val color : Color = Color.YELLOW) : Serializable