package ru.geekbrains.noteapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.geekbrains.noteapp.viewmodel.viewstate.BaseViewState
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.VIEW_MODEL_BUNDLE
import ru.geekbrains.noteapp.adapter.NoteAdapter
import ru.geekbrains.noteapp.adapter.OnItemClickListener
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.viewmodel.viewmodel.BaseViewModel
import ru.geekbrains.noteapp.viewmodel.listener.OpenFragmentListener


class NoteFragment : Fragment() {

    lateinit var viewModel: BaseViewModel
    lateinit var noteAdapter: NoteAdapter
    lateinit var notesRecycler: RecyclerView

    private var openFragmentListener: OpenFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenFragmentListener) {
            openFragmentListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        bindView(view)
        initViewModel()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getSerializable(VIEW_MODEL_BUNDLE)?.let {
            viewModel.changeState(it as List<Note>)
        }
    }

    private fun bindView(view: View) {
        noteAdapter = NoteAdapter(object: OnItemClickListener{
            override fun onItemClick(note: Note) {
                openNoteEditor(note)
            }
        })
        notesRecycler = view.findViewById(R.id.recycler_notes)
        notesRecycler.adapter = noteAdapter

        view.findViewById<FloatingActionButton>(R.id.fab_base_app).setOnClickListener { v ->
            openNoteEditor()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        viewModel.viewState().observe(viewLifecycleOwner, Observer<BaseViewState> { state ->
            state.notes?.let {
                noteAdapter.values = it
            }
        })
    }

    private fun openNoteEditor(note: Note? = null) {
        openFragmentListener?.replaceFragment(NoteEditorFragment.newInstance(note))
    }

    companion object {
        @JvmStatic
        fun newInstance(): NoteFragment = NoteFragment()
    }

}
