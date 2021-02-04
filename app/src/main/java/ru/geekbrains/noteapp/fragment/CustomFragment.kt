package ru.geekbrains.noteapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.viewmodel.listener.OpenFragmentListener
import ru.geekbrains.noteapp.viewmodel.viewmodel.BaseViewModel
import ru.geekbrains.noteapp.viewstate.BaseViewState


abstract class CustomFragment <T, VS : BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, VS>
    abstract val layoutRes: Int

    abstract protected var _ui: ViewBinding
    val ui get() = _ui

    protected var openFragmentListener: OpenFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenFragmentListener) {
            openFragmentListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.viewState().observe(this) { state ->
            state.apply {
                data?.let {onDataExist(it)}
                error?.let {onError(it)}
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = ui.root  //inflater.inflate(layoutRes, container, false)
        bindView(view)
        return view
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _ui = null
//    }

    abstract fun bindView(view: View)

    abstract fun onDataExist(data: T)

    protected fun onError(error: Throwable) {
        error.message?.let { showMessage(it) }
    }

    protected fun showMessage(msg: String) = AlertDialog.Builder(ui.root.context)
        .setTitle(msg)
        .setCancelable(false)
        .setPositiveButton(R.string.button_ok) { dialog, id -> }.create().show()
}