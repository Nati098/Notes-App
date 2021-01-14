package ru.geekbrains.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geekbrains.noteapp.R


class NoteEditorFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_editor, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NoteEditorFragment().apply {
                arguments = Bundle().apply {
                    // TODO
                }
            }
    }
}