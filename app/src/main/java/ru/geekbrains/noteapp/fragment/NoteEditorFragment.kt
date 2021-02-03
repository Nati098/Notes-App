package ru.geekbrains.noteapp.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlinx.android.synthetic.main.fragment_note_editor.view.*
import ru.geekbrains.noteapp.NOTE_BUNDLE
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.databinding.FragmentNoteEditorBinding
import ru.geekbrains.noteapp.databinding.FragmentNoteListBinding
import ru.geekbrains.noteapp.model.data.Color
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.viewmodel.viewmodel.NoteEditorViewModel
import ru.geekbrains.noteapp.viewstate.NoteEditorViewState
import java.util.*


private const val SAVE_DELAY = 2000L

class NoteEditorFragment : CustomFragment<Note?, NoteEditorViewState>() {

    override val viewModel: NoteEditorViewModel by lazy { ViewModelProvider(this).get(NoteEditorViewModel::class.java) }
    override val layoutRes: Int = R.layout.fragment_note_editor
    lateinit override var _ui: ViewBinding

    //    private lateinit var toolbar: Toolbar

    private var note: Note? = null
    private val textChangedListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _ui = FragmentNoteEditorBinding.inflate(inflater)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = arguments?.getString(NOTE_BUNDLE)
        noteId?.let {
            viewModel.loadNote(it)
        }

        // if (noteId != null) action bar title = "Edit note"
    }

    override fun bindView(view: View) {
//        toolbar = ui.root..toolbar
    }

    private fun initView() {
        Log.d("EDITOR", note.toString())
        note?.let {
            Log.d("EDITOR", it.title)
            ui.root.text_input_note_title.setText(it.title)
            ui.root.edit_text_note_content.setText(it.content)

            val color = when (it.color) {
                Color.WHITE -> R.color.color_white
                Color.PURPLE -> R.color.color_purple
                Color.YELLOW -> R.color.color_yellow
            }

            ui.root.layout_note_editor.setBackgroundResource(color)
        }

        ui.root.text_input_note_title.addTextChangedListener(textChangedListener)
        ui.root.edit_text_note_content.addTextChangedListener(textChangedListener)

//        toolbar.title = if (note != null) {
//                SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
//            } else {
//            getString(R.string.new_note_title)
//        }
    }

    override fun onDataExist(data: Note?) {
        note = data
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_save -> {
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.alert_dialog_title_save))
                .setPositiveButton(R.string.button_yes) { dialog, which ->
                    saveNote()
                    openFragmentListener?.popBackStack()
                }
                .setNegativeButton(R.string.button_no) { _, _ -> openFragmentListener?.popBackStack() }
            builder.show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun createNote() : Note = Note(
        id = UUID.randomUUID().toString(),
        title = ui.root.text_input_note_title.text.toString(),
        content = ui.root.edit_text_note_content.text.toString(),
        color = Color.YELLOW,       // TODO: choice of colors
        lastChanged = Date()
    )

    private fun saveNote() {
        if (ui.root.text_input_note_title.text == null || ui.root.edit_text_note_content.text!!.isEmpty()) return

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                note = note?.copy(
                    title = ui.root.text_input_note_title.text.toString(),
                    content = ui.root.edit_text_note_content.text.toString(),
                    lastChanged = Date()
                ) ?: createNote()

                note?.let { viewModel.saveChanges(it) }
            }
        }, SAVE_DELAY)
    }

    companion object {
        @JvmStatic
        fun newInstance(noteId: String? = null): NoteEditorFragment {
            val arguments = Bundle()
            arguments.putString(NOTE_BUNDLE, noteId)
            val fragment = NoteEditorFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}