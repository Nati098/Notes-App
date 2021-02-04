package ru.geekbrains.noteapp

import android.content.Context
import androidx.core.content.ContextCompat
import ru.geekbrains.noteapp.model.data.Color
import java.text.SimpleDateFormat
import java.util.*

const val VIEW_MODEL_BUNDLE = "view_model_bundle"
const val NOTE_BUNDLE = "note_bundle"

const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

object LoggerMode {
    var VERBOSE = true
    var DEBUG = VERBOSE && true
}

// EXTENSIONS
fun Date.format() : String = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(this)

fun Color.getColorRes(context: Context) : Int =
    ContextCompat.getColor(context, when(this) {
        Color.WHITE -> R.color.color_white
        Color.PURPLE -> R.color.color_purple
        Color.YELLOW -> R.color.color_yellow
    })