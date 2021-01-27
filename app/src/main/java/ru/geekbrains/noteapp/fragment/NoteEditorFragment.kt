package ru.geekbrains.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geekbrains.noteapp.NOTE_BUNDLE
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.model.data.Color
import ru.geekbrains.noteapp.model.data.Note


class NoteEditorFragment : Fragment() {

    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_note_editor, container, false)

        bindView(view)
        initView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        note = arguments?.getParcelable(NOTE_BUNDLE)
    }

    private fun bindView(view: View) {
        // TODO
    }

    private fun initView() {
        if (note != null) {
            // TODO


            val color = when(note?.color) {
                Color.WHITE -> R.color.color_white
                Color.PURPLE -> R.color.color_purple
                Color.YELLOW -> R.color.color_yellow
                else -> R.color.color_yellow
            }

            // TODO: set background

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(note: Note?): NoteEditorFragment {
            val arguments = Bundle()
            arguments.putParcelable(NOTE_BUNDLE, note)
            val fragment = NoteEditorFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}