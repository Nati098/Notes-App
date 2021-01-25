package ru.geekbrains.noteapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.noteapp.BaseViewState
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.adapter.NoteAdapter
import ru.geekbrains.noteapp.model.Repository.NOTES_BUNDLE
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.viewmodel.BaseViewModel
import java.io.Serializable


class NoteFragment : Fragment() {

    lateinit var viewModel : BaseViewModel
    lateinit var noteAdapter : NoteAdapter
    lateinit var notesRecycler: RecyclerView

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

        arguments?.let { args ->
            args.getSerializable(NOTES_BUNDLE)?.let {
                viewModel.changeState(it as List<Note>)
            }
        }
    }

    private fun bindView(view : View) {
        noteAdapter = NoteAdapter()
        notesRecycler = view.findViewById(R.id.recycler_notes)
        notesRecycler.adapter = noteAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        viewModel.viewState().observe(viewLifecycleOwner, Observer<BaseViewState> { state ->
            state?.let {
                noteAdapter.values = state.notes
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(notes : List<Note>) : NoteFragment{
            val arguments = Bundle()
            arguments.putSerializable(NOTES_BUNDLE, notes as Serializable)
            val fragment = NoteFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

}
