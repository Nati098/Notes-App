package ru.geekbrains.noteapp

const val VIEW_MODEL_BUNDLE = "view_model_bundle"
const val NOTE_BUNDLE = "note_bundle"

const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

const val SAVE_DELAY = 2000L

object LoggerMode {
    var VERBOSE = true
    var DEBUG = VERBOSE && true
}