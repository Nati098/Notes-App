package ru.geekbrains.noteapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.geekbrains.noteapp.viewstate.NoteListViewState
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.adapter.NoteAdapter
import ru.geekbrains.noteapp.adapter.OnItemClickListener
import ru.geekbrains.noteapp.databinding.FragmentNoteListBinding
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.viewmodel.viewmodel.NoteListViewModel


class NoteListFragment : CustomFragment<List<Note>?, NoteListViewState>() {

    override val viewModel: NoteListViewModel by lazy {ViewModelProvider(this).get(NoteListViewModel::class.java)}
    override val layoutRes: Int = R.layout.fragment_note_list
    lateinit override var _ui: ViewBinding

    lateinit var noteAdapter: NoteAdapter
    lateinit var notesRecycler: RecyclerView

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        arguments?.getSerializable(VIEW_MODEL_BUNDLE)?.let {
//            viewModel.changeState(it as List<Note>)
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _ui = FragmentNoteListBinding.inflate(inflater)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun bindView(view: View) {
        noteAdapter = NoteAdapter(object: OnItemClickListener{
            override fun onItemClick(note: Note) {
                openNoteEditorScreen(note)
            }
        })
        notesRecycler = view.findViewById(R.id.recycler_notes)
        notesRecycler.adapter = noteAdapter

        view.findViewById<FloatingActionButton>(R.id.fab_base_app).setOnClickListener { v ->
            openNoteEditorScreen()
        }
    }

//    private fun initViewModel() {
//        viewModel.viewState().observe(viewLifecycleOwner, Observer<BaseViewState> { state ->
//            state.notes?.let {
//                noteAdapter.values = it
//            }
//        })
//    }

    override fun onDataExist(data: List<Note>?) {
        data?.let {
            noteAdapter.values = it
        }
    }

    private fun openNoteEditorScreen(note: Note? = null) {
        openFragmentListener?.replaceFragment(NoteEditorFragment.newInstance(note?.id))
    }

    companion object {
        @JvmStatic
        fun newInstance(): NoteListFragment = NoteListFragment()
    }

}
