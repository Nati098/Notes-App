package ru.geekbrains.noteapp.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import ru.geekbrains.noteapp.DATE_TIME_FORMAT
import ru.geekbrains.noteapp.NOTE_BUNDLE
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.model.data.Color
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.viewmodel.listener.OpenFragmentListener
import java.text.SimpleDateFormat
import java.util.*


class NoteEditorFragment : Fragment() {

    private var note: Note? = null
    private lateinit var layout: CoordinatorLayout
    private lateinit var titleInput: TextInputEditText
    private lateinit var contentInput: EditText

    private var openFragmentListener: OpenFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_editor, container, false)
        setHasOptionsMenu(true);

        bindView(view)
        initView()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        note = arguments?.getParcelable(NOTE_BUNDLE)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenFragmentListener) {
            openFragmentListener = context
        }
    }

    private fun bindView(view: View) {
        layout = view.findViewById(R.id.layout_note_editor)

        titleInput = view.findViewById(R.id.text_input_note_title)
        contentInput = view.findViewById(R.id.edit_text_note_content)
    }

    private fun initView() {
        note?.let {
            titleInput.setText(it.title)
            contentInput.setText(it.content)

            val color = when(it.color) {
                Color.WHITE -> R.color.color_white
                Color.PURPLE -> R.color.color_purple
                Color.YELLOW -> R.color.color_yellow
            }
            layout.setBackgroundColor(color)
        }

//        toolbar.title = if (note != null) {
//                SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
//            } else {
//            getString(R.string.new_note_title)
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.action_save -> {
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.alert_dialog_title_save))
                .setPositiveButton(R.string.button_yes) { dialog, which ->
                    // TODO: save note
                    openFragmentListener?.popBackStack()
                }
                .setNegativeButton(R.string.button_no) { _, _ ->  openFragmentListener?.popBackStack()}
            builder.show()
            true
        }
        else ->  super.onOptionsItemSelected(item)
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